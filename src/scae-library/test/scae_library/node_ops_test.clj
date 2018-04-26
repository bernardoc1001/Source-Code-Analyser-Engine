(ns scae-library.node-ops-test
  (:require [clojure.test :refer :all]
            [scae-library.sample-inputs :refer [ast-entry-type-data-set
                                                get-parsed-node-result-data-set
                                                sample-identifier-node
                                                get-nth-child-data-set
                                                node-contains-data-set]]))

(deftest entry-type-check?-test
  (testing "Testing the checking of the ast entry type")
  (let [entry-type-check-data (:entry-type-check ast-entry-type-data-set)]
    (is (= (@#'scae-library.node-ops/entry-type-check?
             (:ast-entry entry-type-check-data)
             (:key entry-type-check-data))
           true))
    (is (= (@#'scae-library.node-ops/entry-type-check?
             {:invalid-data nil}
             :invalid-key)
           nil))))

(deftest node?-test
  (testing "Testing the check if an ast entry is of type node")
  (let [node-check-data (:node-check ast-entry-type-data-set)]
    (is (= (@#'scae-library.node-ops/node?
             (:ast-entry node-check-data))
           true))
    (is (= (@#'scae-library.node-ops/node?
             {:invalid-data nil})
           nil))))

(deftest token?-test
  (testing "Testing the check if an ast entry is of type token")
  (let [token-check-data (:token-check ast-entry-type-data-set)]
    (is (= (@#'scae-library.node-ops/token?
             (:ast-entry token-check-data))
           true))
    (is (= (@#'scae-library.node-ops/token?
             {:invalid-data nil})
           nil))))

(deftest st-func-calls?-test
  (testing "Testing the check if an ast entry are symbol table function calls")
  (let [st-func-calls-check-data (:st-func-calls-check ast-entry-type-data-set)]
    (is (= (@#'scae-library.node-ops/st-func-calls?
             (:ast-entry st-func-calls-check-data))
           true))
    (is (= (@#'scae-library.node-ops/st-func-calls?
             {:invalid-data nil})
           nil))))

;;todo implement line numbers
(deftest line-number?-test
  (testing "Testing the check if an ast entry is of type line-number")
  (let [line-number-check-data (:line-number-check ast-entry-type-data-set)]
    (is (= (@#'scae-library.node-ops/line-number?
             (:ast-entry line-number-check-data))
           true))
    (is (= (@#'scae-library.node-ops/line-number?
             {:invalid-data nil})
           nil))))


(deftest get-parsed-node-result-test
  (testing "Testing getting the pared node result")
  (let [non-string-result (:non-string-result get-parsed-node-result-data-set)
        string-result (:string-result get-parsed-node-result-data-set)]
    (is (= (@#'scae-library.node-ops/get-parsed-node-result (:ast-node non-string-result))
           (:expected-result non-string-result)))
    (is (= (@#'scae-library.node-ops/get-parsed-node-result (:ast-node string-result))
           (:expected-result string-result)))))

(deftest get-node-name-test
  (testing "Testing getting the node name")
  (is (= (@#'scae-library.node-ops/get-node-name (:ast-entry sample-identifier-node))
         (:parsed-node-name sample-identifier-node)))
  (is (= (@#'scae-library.node-ops/get-node-name {})
         nil)))

(deftest get-node-children-test
  (testing "Testing getting the node children")
  (is (= (@#'scae-library.node-ops/get-node-children (:ast-entry sample-identifier-node))
         (:parsed-node-result sample-identifier-node)))
  (is (= (@#'scae-library.node-ops/get-node-name {})
         nil)))

(deftest get-nth-child-test
  (testing "Testing getting the nth child")
  (let [ast-entry (:ast-entry get-nth-child-data-set)]
    (is (= (@#'scae-library.node-ops/get-nth-child
             ast-entry
             0)
           {:parsed-node-name   "a-child-node",
            :parsed-node-result '({:token-key   :first,
                                   :token-value "first",
                                   :token-type  :token})}))
    (is (= (@#'scae-library.node-ops/get-nth-child
             ast-entry
             1)
           {:parsed-node-name   "a-child-node",
            :parsed-node-result '({:token-key   :second,
                                   :token-value "second",
                                   :token-type  :token})}))
    ;;test for type token
    (is (= (@#'scae-library.node-ops/get-nth-child
             ast-entry
             5)
           {:token-key :FIRST_TOKEN_CHILD,
            :token-value "FIRST_TOKEN_CHILD",
            :token-type :token}))

    (is (= (@#'scae-library.node-ops/get-nth-child
             ast-entry
             6)
           {:token-key :SECOND_TOKEN_CHILD,
            :token-value "SECOND_TOKEN_CHILD",
            :token-type :token}))

    (is (= (@#'scae-library.node-ops/get-nth-child
             ast-entry
             7)
           {:parsed-node-name   "a-different-child-node",
            :parsed-node-result '({:token-key   :second-different,
                                   :token-value "second-different",
                                   :token-type  :token})}))
    (is (= (@#'scae-library.node-ops/get-nth-child
             ast-entry
             2)
           {:parsed-node-name   "a-different-child-node",
            :parsed-node-result '({:token-key   :first-different,
                                   :token-value "first-different",
                                   :token-type  :token})}))
    ;;out of bounds test
    (is (= (@#'scae-library.node-ops/get-nth-child ast-entry 999)
           nil))

    ;;negative index
    (is (= (@#'scae-library.node-ops/get-nth-child ast-entry -1)
           nil))))

(deftest get-nth-named-child-test
  (testing "Testing getting the nth named child")
  (let [ast-entry (:ast-entry get-nth-child-data-set)]
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             0
             "a-child-node")
           {:parsed-node-name   "a-child-node",
            :parsed-node-result '({:token-key   :first,
                                   :token-value "first",
                                   :token-type  :token})}))
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             1
             "a-child-node")
           {:parsed-node-name   "a-child-node",
            :parsed-node-result '({:token-key   :second,
                                   :token-value "second",
                                   :token-type  :token})}))
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             2
             "a-child-node")
           {:parsed-node-name   "a-child-node",
            :parsed-node-result '({:token-key   :third,
                                   :token-value "third",
                                   :token-type  :token})}))

    ;; The next test is of interest , as the first instance of "a-different-child-node"
    ;; is the 3rd entry
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             0
             "a-different-child-node")
           {:parsed-node-name   "a-different-child-node",
            :parsed-node-result '({:token-key   :first-different,
                                   :token-value "first-different",
                                   :token-type  :token})}))
    ;;this is the last (fifth) entry in the list
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             1
             "a-different-child-node")
           {:parsed-node-name   "a-different-child-node",
            :parsed-node-result '({:token-key   :second-different,
                                   :token-value "second-different",
                                   :token-type  :token})}))

    ;;test for type token
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             0
             :FIRST_TOKEN_CHILD)
           {:token-key :FIRST_TOKEN_CHILD,
            :token-value "FIRST_TOKEN_CHILD",
            :token-type :token}))

    ;;out of bounds test
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             999
             "a-different-child-node")
           nil))

    ;;negative index
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             -1
             "a-different-child-node")
           nil))

    ;; non-existent child
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             0
             "a-non-existent-child-name")
           nil))

    ;; non-existent child of type token
    (is (= (@#'scae-library.node-ops/get-nth-named-child
             ast-entry
             0
             :a-non-existent-child-name)
           nil))))

(deftest node-contains-top-layer-child?-test
  (testing "Testing if the node contains a certain child as a direct descendant")
  (let [ast-entry (:ast-entry node-contains-data-set)]
    (is (= (@#'scae-library.node-ops/node-contains-top-layer-child?
             ast-entry
             "identifier")
           true))
    (is (= (@#'scae-library.node-ops/node-contains-top-layer-child?
             ast-entry
             "statement-prime")
           true))
    ;;Test for child of type token
    (is (= (@#'scae-library.node-ops/node-contains-top-layer-child?
             ast-entry
             :A_TOKEN_CHILD)
           true))

    ;;below is false as the node is nested and not in the top-layer
    (is (= (@#'scae-library.node-ops/node-contains-top-layer-child?
             ast-entry
             "optional-lbr")
           nil))
    ;;below is false for a child that doesn't exist
    (is (= (@#'scae-library.node-ops/node-contains-top-layer-child?
             ast-entry
             "non-existent-child-name")
           nil))

    ;;below is false for a child that doesn't exist of type token
    (is (= (@#'scae-library.node-ops/node-contains-top-layer-child?
             ast-entry
             :non-existent-child-name)
           nil))))

(deftest node-contains-nested-child?-test
  (testing "Testing if a node contains a certain child anywhere in its nested tree")
  (let [ast-entry (:ast-entry node-contains-data-set)]
    ;;top level true
    (is (= (@#'scae-library.node-ops/node-contains-nested-child?
             ast-entry
             "statement-prime")
           true))
    ;;1 layer of nesting
    (is (= (@#'scae-library.node-ops/node-contains-nested-child?
             ast-entry
             "expression")
           true))
    ;;heavy nesting
    (is (= (@#'scae-library.node-ops/node-contains-nested-child?
             ast-entry
             "fragment-prime")
           true))
    ;;heavy nested test for child of type token
    (is (= (@#'scae-library.node-ops/node-contains-nested-child?
             ast-entry
             :NUM)
           true))
    ;;non-existent child
    (is (= (@#'scae-library.node-ops/node-contains-nested-child?
             ast-entry
             "non-existent-child-name")
           nil))))

(deftest sub-node-contains-top-layer-child?-test
  (testing "Testing if a certain (top-layer) child contains a certain direct child")
  (let [ast-entry (:ast-entry node-contains-data-set)]
    (is (= (@#'scae-library.node-ops/sub-node-contains-top-layer-child?
             ast-entry
             "statement-prime"
             "expression")
           true))

    ;;subnode doesn't contain top layer child
    (is (= (@#'scae-library.node-ops/sub-node-contains-top-layer-child?
             ast-entry
             "identifier"
             "expression")
           nil))
    ;;is nested, not top layer
    (is (= (@#'scae-library.node-ops/sub-node-contains-top-layer-child?
             ast-entry
             "statement-prime"
             "optional-lbr")
           nil))

    ;;test for child node of type token
    (is (= (@#'scae-library.node-ops/sub-node-contains-top-layer-child?
             ast-entry
             "statement-prime"
             :ASSIGN)
           true))

    ;;test for non-existent of type node
    (is (= (@#'scae-library.node-ops/sub-node-contains-top-layer-child?
             ast-entry
             "statement-prime"
             "non-existent-child-name")
           nil))

    ;;test for non-existent of type toke
    (is (= (@#'scae-library.node-ops/sub-node-contains-top-layer-child?
             ast-entry
             "statement-prime"
             :non-existent-child-name)
           nil))

    (is (= (@#'scae-library.node-ops/sub-node-contains-top-layer-child?
             ast-entry
             "non-existent-child-name"
             "expression")
           nil))))

(deftest sub-node-contains-nested-child?-test
  (testing "Testing if a certain (top-layer) child node contains a certain child anywhere in its nested tree")
  (let [ast-entry (:ast-entry node-contains-data-set)]
    (is (= (@#'scae-library.node-ops/sub-node-contains-nested-child?
             ast-entry
             "statement-prime"
             "expression")
           true))

    ;;subnode doesn't contain child
    (is (= (@#'scae-library.node-ops/sub-node-contains-nested-child?
             ast-entry
             "identifier"
             "expression")
           nil))
    ;;is nested, not top layer
    (is (= (@#'scae-library.node-ops/sub-node-contains-nested-child?
             ast-entry
             "statement-prime"
             "optional-lbr")
           true))

    ;;test for child node of type token
    (is (= (@#'scae-library.node-ops/sub-node-contains-nested-child?
             ast-entry
             "statement-prime"
             :ASSIGN)
           true))

    ;;Test heavy nesting
    (is (= (@#'scae-library.node-ops/sub-node-contains-nested-child?
             ast-entry
             "statement-prime"
             :NUM)
           true))

    ;;test for non-existent of type node
    (is (= (@#'scae-library.node-ops/sub-node-contains-nested-child?
             ast-entry
             "statement-prime"
             "non-existent-child-name")
           nil))

    ;;test for non-existent of type toke
    (is (= (@#'scae-library.node-ops/sub-node-contains-nested-child?
             ast-entry
             "statement-prime"
             :non-existent-child-name)
           nil))

    (is (= (@#'scae-library.node-ops/sub-node-contains-nested-child?
             ast-entry
             "non-existent-child-name"
             "expression")
           nil))))

