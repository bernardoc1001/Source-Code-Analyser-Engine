(ns scae-library.abstract-syntax-tree-test
  (:require [clojure.test :refer :all]
            [scae-library.abstract-syntax-tree :refer :all]
            [scae-library.sample-inputs :refer [sample-ccal-rulebook-string
                                                tokenised-code-sample-ccal-code-4
                                                ast-sample-ccal-code-4
                                                count-tokens-data-set
                                                parse-single-option-data-set]]
            [scae-library.common :as testing-common]
            [clojure.data.json :as json]))


(deftest create-abstract-syntax-tree-test
  (testing "Testing the creation of an Abstract Syntax Tree")
  (let [production-rules (:productions (json/read-str sample-ccal-rulebook-string :key-fn keyword))
        function-call-ccal-4 (testing-common/strip-st-functions
                               (@#'scae-library.abstract-syntax-tree/create-abstract-syntax-tree
                                 tokenised-code-sample-ccal-code-4
                                 production-rules))
        expected-result (testing-common/strip-st-functions ast-sample-ccal-code-4)]
    (is (= function-call-ccal-4
           expected-result))))


(deftest code->node-test
  (testing "Testing the conversion of code to an AST node")
  (let [input "(function-list)"
        expected-output {:node-name "function-list", :node-value [['(function) '(function-list)] [[]]]}]
    (is (= (@#'scae-library.abstract-syntax-tree/code->node input) expected-output))))

(deftest count-tokens-test
  (testing "Testing the counting of how many tokens are in a node")
  (let [four-tokens (:four-tokens count-tokens-data-set)
        zero-tokens (:zero-tokens count-tokens-data-set)]
    (is (= (@#'scae-library.abstract-syntax-tree/count-tokens
             four-tokens)
           4))
    (is (= (@#'scae-library.abstract-syntax-tree/count-tokens
             zero-tokens)
           0))))

(deftest parse-single-option-test
  (testing "Testing the parsing of a single option in a production rule")
  (let [empty-option-list (:empty-option-list parse-single-option-data-set)
        epsilon-transition (:epsilon-transition parse-single-option-data-set)
        one-in-option-vec-multiple-tokens (:one-in-option-vec-multiple-tokens parse-single-option-data-set)
        multiple-in-option-vec (:multiple-in-option-vec parse-single-option-data-set)
        one-in-option-vec-one-in-tokens (:one-in-option-vec-one-in-tokens parse-single-option-data-set)]
    ;;empty list check
    (is (= (@#'scae-library.abstract-syntax-tree/parse-single-option
             (:tokens empty-option-list)
             (:option-vec empty-option-list))
           (:expected-result empty-option-list)))

    ;;epsilon transition check
    (is (= (@#'scae-library.abstract-syntax-tree/parse-single-option
             (:tokens epsilon-transition)
             (:option-vec epsilon-transition))
           (:expected-result epsilon-transition)))

    ;;1 token in option vector, multiple tokens in inout stream check
    (is (= (@#'scae-library.abstract-syntax-tree/parse-single-option
             (:tokens one-in-option-vec-multiple-tokens)
             (:option-vec one-in-option-vec-multiple-tokens))
           (:expected-result one-in-option-vec-multiple-tokens)))

    ;;multiple tokens multiple in option vec check
    (is (= (@#'scae-library.abstract-syntax-tree/parse-single-option
             (:tokens multiple-in-option-vec)
             (:option-vec multiple-in-option-vec))
           (:expected-result multiple-in-option-vec)))

    ;;single token in option vector and input stream check
    (is (= (@#'scae-library.abstract-syntax-tree/parse-single-option
             (:tokens one-in-option-vec-one-in-tokens)
             (:option-vec one-in-option-vec-one-in-tokens))
           (:expected-result one-in-option-vec-one-in-tokens)))))

(deftest parse-production-rule-test
  (testing "Testing the parsing of all potential options in a production rule")
  (let [production-rule (:scae-entry-point (:productions (json/read-str sample-ccal-rulebook-string :key-fn keyword)))
        function-call (testing-common/strip-st-functions
                        (@#'scae-library.abstract-syntax-tree/parse-production-rule
                          tokenised-code-sample-ccal-code-4
                          production-rule))
        expected-result (testing-common/strip-st-functions ast-sample-ccal-code-4)]
    (is (= function-call
           expected-result))))

