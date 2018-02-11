(ns scae-library.core-test
  (:require [clojure.test :refer :all]
            [scae-library.core :refer :all]
            [scae-library.sample-inputs :refer [sample-token-json
                                                sample-code-1-txt
                                                tokenised-sample-code-1]]))

;;TODO expand this test as the functionality of the project increases
(deftest analyse-source-code-test
  (testing "Testing analysing source code"
    (is (= (analyse-source-code {:code sample-code-1-txt
                                 :rulebook sample-token-json})
           tokenised-sample-code-1))))
