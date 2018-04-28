(ns scae-library.style-analyser-test
  (:require [clojure.test :refer :all]
            [scae-library.sample-inputs :refer [quote-first-parsed-node-result-data-set
                                                parse-style-rules-data-set]]))

(deftest quote-first-parsed-node-result-test
  (testing "Testing quoting the first parsed-node-result")
  (let [ast-entry (:ast-entry quote-first-parsed-node-result-data-set)
        expected-result (:expected-result quote-first-parsed-node-result-data-set)]
    (is (= (@#'scae-library.style-analyser/quote-first-parsed-node-result
             ast-entry)
           expected-result))))

(deftest parse-style-rules-test
  (testing "Testing parsing the style rules")
  (let [node-based-style-rules (:node-based-style-rules parse-style-rules-data-set)
        no-match-for-style-rule (:no-match-for-style-rule parse-style-rules-data-set)
        node-has-no-children (:node-has-no-children parse-style-rules-data-set)
        style-rule-match (:style-rule-match parse-style-rules-data-set)]
    (is (= (@#'scae-library.style-analyser/parse-style-rules
             (:ast-entry no-match-for-style-rule)
             node-based-style-rules)
           (:expected-result no-match-for-style-rule)))
    (is (= (@#'scae-library.style-analyser/parse-style-rules
             (:ast-entry node-has-no-children)
             node-based-style-rules)
           (:expected-result node-has-no-children)))
    (is (= (@#'scae-library.style-analyser/parse-style-rules
             (:ast-entry style-rule-match)
             node-based-style-rules)
           (:expected-result style-rule-match)))))

(deftest analyse-style
  (testing "Testing analysing code style")
  (let [style-rules (:style-rules parse-style-rules-data-set)
        no-match-for-style-rule (:no-match-for-style-rule parse-style-rules-data-set)
        node-has-no-children (:node-has-no-children parse-style-rules-data-set)
        style-rule-match (:style-rule-match parse-style-rules-data-set)]
    (is (= (@#'scae-library.style-analyser/analyse-style
             (:ast-entry no-match-for-style-rule)
             style-rules)
           (:expected-result no-match-for-style-rule)))
    (is (= (@#'scae-library.style-analyser/analyse-style
             (:ast-entry node-has-no-children)
             style-rules)
           (:expected-result node-has-no-children)))
    (is (= (@#'scae-library.style-analyser/analyse-style
             (:ast-entry style-rule-match)
             style-rules)
           (:expected-result style-rule-match)))))