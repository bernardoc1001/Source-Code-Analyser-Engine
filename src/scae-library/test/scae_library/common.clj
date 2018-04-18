(ns ^{:author "Bernard O'Connor"
      :doc "This is a utility namespace used by other testing namespaces.
      This namespace itself does not contain any tests"}
scae-library.common
  (:require [com.rpl.specter :refer :all]))

(defn strip-st-functions
  "Replaces the value for :st-func-calls with a string stating why it was stripped.
  This is neccessary as anonymous functions are replaced with an auto-generated
  reference. So if two identical anonymous functions are stored two separate
  data strucutres, they will fail any comparison as the references are different.
  This is why we must strip out these functions."
  [ast-tree]
  (setval
    [(recursive-path [] p
                     [(walker :st-func-calls) (stay-then-continue [:st-func-calls p])])
     :st-func-calls] "REPLACED FOR TESTING PURPOSES, SINCE ANONYMOUS FUNCTIONS CAN NOT BE COMPARED"
    ast-tree))