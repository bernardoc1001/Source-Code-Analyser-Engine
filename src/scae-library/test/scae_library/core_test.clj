(ns scae-library.core-test
  (:require [clojure.test :refer :all]
            [scae-library.core :refer :all]
            [scae-library.sample-inputs :refer [sample-ccal-rulebook-string
                                                sample-ccal-code-4-string
                                                sample-ccal-code-4-rulebook-1-suggestion
                                                ast-sample-ccal-code-4]]
            [scae-library.common :as testing-common]))


(deftest analyse-source-code-test
  (testing "Testing analysing source code"
    (is (= (@#'scae-library.core/analyse-source-code {:code     sample-ccal-code-4-string
                                 :rulebook sample-ccal-rulebook-string})
           sample-ccal-code-4-rulebook-1-suggestion))))

(deftest generate-ast-test
  (testing "Testing analysing source code"
    (let [expected-result (testing-common/strip-st-functions ast-sample-ccal-code-4)]
      (is (= (testing-common/strip-st-functions
               (@#'scae-library.core/generate-ast {:code     sample-ccal-code-4-string
                                                   :rulebook sample-ccal-rulebook-string}))
             expected-result)))))
