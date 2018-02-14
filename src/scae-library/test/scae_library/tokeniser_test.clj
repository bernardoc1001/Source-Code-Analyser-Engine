(ns scae-library.tokeniser-test
  (:require [clojure.test :refer :all]
            [scae-library.tokeniser :refer :all]
            [scae-library.sample-inputs :refer [sample-rulebook-json
                                                sample-code-1-txt
                                                tokenised-sample-code-1
                                                sample-token-definitions-output
                                                sample-insert-token-type-input]]
            [clojure.data.json :as json]))



(def sample-token-vector (:tokens (json/read-str sample-rulebook-json :key-fn keyword)))

(deftest insert-token-type-test
  (testing "Testing inserting the token type into the token definitions"
    (let [input sample-insert-token-type-input
          expected-output sample-token-definitions-output]
      (is (= (@#'scae-library.tokeniser/insert-token-type input) expected-output)))))


(deftest get-token-definitions-test
  (testing "Testing retrieving token definitions from the inputted json"
    (is (= (@#'scae-library.tokeniser/get-token-definitions sample-token-vector) sample-token-definitions-output))))

(deftest val->token-test
  (testing "Testing transforming matched values to their corresponding tokens"
    (let [integer-token {:token-key :ID,
                         :token-value "integer",
                         :token-type :token}
          space-token  {:token-key :SPACE,
                        :token-value " ",
                        :token-type :skip}]
      (is (= (@#'scae-library.tokeniser/val->token "integer" sample-token-definitions-output) integer-token))
      (is (= (@#'scae-library.tokeniser/val->token " " sample-token-definitions-output) space-token))
      (is (= (@#'scae-library.tokeniser/val->token "no-match" sample-token-definitions-output) nil)))))

(deftest tokenise-code-test
  (testing "Testing tokenising sample code"
    (is (= (@#'scae-library.tokeniser/tokenise-code
             sample-code-1-txt
             sample-token-vector)
           tokenised-sample-code-1))))