(ns scae-library.core-test
  (:require [clojure.test :refer :all]
            [scae-library.core :refer :all]
            [scae-library.sample-inputs :refer [sample-ccal-rulebook-string
                                                sample-ccal-code-4-string
                                                sample-ccal-code-4-rulebook-1-suggestion]]))

;;TODO expand this test as the functionality of the project increases
(deftest analyse-source-code-test
  (testing "Testing analysing source code"
    (is (= (analyse-source-code {:code     sample-ccal-code-4-string
                                 :rulebook sample-ccal-rulebook-string})
           sample-ccal-code-4-rulebook-1-suggestion))))
