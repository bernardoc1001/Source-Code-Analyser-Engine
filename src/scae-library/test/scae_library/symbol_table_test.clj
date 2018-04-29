(ns scae-library.symbol-table-test
  (:require [clojure.test :refer :all]
            [scae-library.sample-inputs :refer [push-previous-scope-name-data-set
                                                pop-previous-scope-name-data-set
                                                current-scope-name-data-set
                                                scope-entry-data-set
                                                create-symbol-table-data-set]]))

(deftest push-previous-scope-name-stack!-test
  (testing "Testing pushing the previous scope name to the stack of scopes")
  (let [empty-stack-table (:push-to-empty-stack push-previous-scope-name-data-set)
        non-empty-stack-table (:push-to-non-empty-stack push-previous-scope-name-data-set)]

    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before empty-stack-table))
    (is (= (@#'scae-library.symbol-table/push-previous-scope-name-stack!
             (:scope-name empty-stack-table))
           (:symbol-table-after empty-stack-table)))

    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before non-empty-stack-table))
    (is (= (@#'scae-library.symbol-table/push-previous-scope-name-stack!
             (:scope-name non-empty-stack-table))
           (:symbol-table-after non-empty-stack-table)))
    (@#'scae-library.symbol-table/reset-symbol-table!)))

(deftest pop-previous-scope-name-stack!-test
  (testing "Testing popping the previous scope name off the stack of scopes")
  (let [multiple-in-stack-table (:multiple-in-stack pop-previous-scope-name-data-set)
        single-in-stack-table (:single-in-stack pop-previous-scope-name-data-set)
        none-in-stack-table (:none-in-stack pop-previous-scope-name-data-set)]

    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before multiple-in-stack-table))
    (is (and
          (= (@#'scae-library.symbol-table/pop-previous-scope-name-stack!)
             (:expected-result multiple-in-stack-table))
          (= @@#'scae-library.symbol-table/symbol-table
             (:symbol-table-after multiple-in-stack-table))))

    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before single-in-stack-table))
    (is (and
          (= (@#'scae-library.symbol-table/pop-previous-scope-name-stack!)
             (:expected-result single-in-stack-table))
          (= @@#'scae-library.symbol-table/symbol-table
             (:symbol-table-after single-in-stack-table))))

    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before none-in-stack-table))
    (is (and
          (= (@#'scae-library.symbol-table/pop-previous-scope-name-stack!)
             (:expected-result none-in-stack-table))
          (= @@#'scae-library.symbol-table/symbol-table
             (:symbol-table-after none-in-stack-table))))

    (@#'scae-library.symbol-table/reset-symbol-table!)))

(deftest get-current-scope-name-test
  (testing "Testing getting the current scope name")
  (let [get-scope-name-table (:get-current-scope-name current-scope-name-data-set)]
    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table get-scope-name-table))
    (is (= (@#'scae-library.symbol-table/get-current-scope-name)
           (:expected-result get-scope-name-table)))

    (@#'scae-library.symbol-table/reset-symbol-table!)))

(deftest set-current-scope-name!-test
  (testing "Testing setting the current scope name")
  (let [set-scope-name-table (:set-current-scope-name current-scope-name-data-set)]

    ;;test the default
    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before set-scope-name-table))
    (is (= (@#'scae-library.symbol-table/set-current-scope-name!
             "scope-1")
           (:symbol-table-after-save-previous set-scope-name-table)))

    ;;test with explicitly saving the previous name
    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before set-scope-name-table))
    (is (= (@#'scae-library.symbol-table/set-current-scope-name!
             "scope-1"
             true)
           (:symbol-table-after-save-previous set-scope-name-table)))

    ;;don't save previous scope name
    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before set-scope-name-table))
    (is (= (@#'scae-library.symbol-table/set-current-scope-name!
             "scope-1"
             false)
           (:symbol-table-after-dont-save-previous set-scope-name-table)))

    (@#'scae-library.symbol-table/reset-symbol-table!)))

(deftest set-current-scope-name-to-previous!-test
  (testing "Testing setting the current scope name to the previous scope name")
  (let [no-previous-scope-on-stack (get-in current-scope-name-data-set
                                           [:set-current-scope-name-to-previous
                                            :no-previous-scope-name-on-stack])
        one-scope-name-on-stack (get-in current-scope-name-data-set
                                        [:set-current-scope-name-to-previous
                                         :one-scope-name-on-stack])

        multiple-scope-names-on-stack (get-in current-scope-name-data-set
                                              [:set-current-scope-name-to-previous
                                               :multiple-scope-names-on-stack])]


    (reset! @#'scae-library.symbol-table/symbol-table
      (:symbol-table-before no-previous-scope-on-stack))
    (is (= (@#'scae-library.symbol-table/set-current-scope-name-to-previous!)
           (:symbol-table-after no-previous-scope-on-stack)))

    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before one-scope-name-on-stack))
    (is (= (@#'scae-library.symbol-table/set-current-scope-name-to-previous!)
           (:symbol-table-after one-scope-name-on-stack)))

    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before multiple-scope-names-on-stack))
    (is (= (@#'scae-library.symbol-table/set-current-scope-name-to-previous!)
           (:symbol-table-after multiple-scope-names-on-stack)))

    (@#'scae-library.symbol-table/reset-symbol-table!)))

(deftest add-to-scope-test
  (testing "Testing adding an entry to a scope")
  (let [no-previous-scope-entries (get-in scope-entry-data-set
                                             [:add-to-scope
                                              :no-previous-st-entries])
           multiple-st-entries (get-in scope-entry-data-set
                                       [:add-to-scope
                                        :multiple-st-entries])]

       (reset! @#'scae-library.symbol-table/symbol-table
                 (:symbol-table-before no-previous-scope-entries))
       (is (= (@#'scae-library.symbol-table/add-to-scope
                (:st-entry-name no-previous-scope-entries)
                (:st-entry-map no-previous-scope-entries)
                (:scope-name no-previous-scope-entries))
              (:symbol-table-after no-previous-scope-entries)))

       (reset! @#'scae-library.symbol-table/symbol-table
                 (:symbol-table-before multiple-st-entries))
       (is (= (@#'scae-library.symbol-table/add-to-scope
                  (:st-entry-name multiple-st-entries)
                  (:st-entry-map multiple-st-entries)
                  (:scope-name multiple-st-entries))
                (:symbol-table-after multiple-st-entries)))

       (@#'scae-library.symbol-table/reset-symbol-table!)))

(deftest get-from-scope-test
  (testing "Testing getting an entry from a scope")
  (let [no-previous-scope-entries (get-in scope-entry-data-set
                                          [:get-from-scope
                                           :no-previous-st-entries])
        multiple-st-entries (get-in scope-entry-data-set
                                    [:get-from-scope
                                     :multiple-st-entries])]
    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before no-previous-scope-entries))
    (is (= (@#'scae-library.symbol-table/get-from-scope
             (:st-entry-name no-previous-scope-entries)
             (:scope-name no-previous-scope-entries))
           (:expected-result no-previous-scope-entries)))

    (reset! @#'scae-library.symbol-table/symbol-table
            (:symbol-table-before multiple-st-entries))
    (is (= (@#'scae-library.symbol-table/get-from-scope
             (:st-entry-name multiple-st-entries)
             (:scope-name multiple-st-entries))
           (:expected-result multiple-st-entries)))


    (@#'scae-library.symbol-table/reset-symbol-table!)))

(deftest create-symbol-table-test
  (testing "testing creating a symbol table from an AST")
  (let [ast-node-with-st-func-calls (:ast-node-with-st-func-calls create-symbol-table-data-set)
        ast-node-without-st-func-calls (:ast-node-without-st-func-cals create-symbol-table-data-set)]
    (@#'scae-library.symbol-table/reset-symbol-table!)

    (is (= (do (@#'scae-library.symbol-table/create-symbol-table
                 (:ast-entry ast-node-with-st-func-calls))
               @@#'scae-library.symbol-table/symbol-table)
           (:symbol-table-after ast-node-with-st-func-calls)))
    ))