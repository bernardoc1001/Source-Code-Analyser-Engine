(ns scae-library.tokeniser-test
  (:require [clojure.test :refer :all]
            [scae-library.tokeniser :refer :all]
            [scae-library.sample-inputs :refer [sample-ccal-rulebook-string
                                                sample-ccal-code-4-string
                                                tokenised-code-sample-ccal-code-4
                                                sample-ccal-rulebook-1-token-definition-with-regex
                                                sample-ccal-rulebook-1-token-and-skip-definition]]
            [clojure.data.json :as json]))



(def sample-token-vector (:tokens (json/read-str sample-ccal-rulebook-string :key-fn keyword)))

(deftest insert-token-type-test
  (testing "Testing inserting the token type into the token definitions"
    (let [input sample-ccal-rulebook-1-token-and-skip-definition
          expected-output            sample-ccal-rulebook-1-token-definition-with-regex]
      (is (= (@#'scae-library.tokeniser/insert-token-type input) expected-output)))))


(deftest get-token-definitions-test
  (testing "Testing retrieving token definitions from the inputted json"
    (is (= (@#'scae-library.tokeniser/get-token-definitions sample-token-vector)
           sample-ccal-rulebook-1-token-definition-with-regex))))

(deftest val->token-test
  (testing "Testing transforming matched values to their corresponding tokens"
    (let [integer-token {:token-key :INT,
                         :token-value "integer",
                         :token-type :token}
          space-token  {:token-key :SPACE,
                        :token-value " ",
                        :token-type :skip}]
      (is (= (@#'scae-library.tokeniser/val->token "integer" sample-ccal-rulebook-1-token-definition-with-regex) integer-token))
      (is (= (@#'scae-library.tokeniser/val->token " " sample-ccal-rulebook-1-token-definition-with-regex) space-token))
      (is (= (@#'scae-library.tokeniser/val->token "no-match" sample-ccal-rulebook-1-token-definition-with-regex) nil)))))

(deftest tokenise-code-test
  (testing "Testing tokenising sample code"
    (is (= (@#'scae-library.tokeniser/tokenise-code sample-ccal-code-4-string sample-token-vector)
           tokenised-code-sample-ccal-code-4))))