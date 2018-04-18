(ns ^{:author "Bernard O'Connor"
      :doc "This is a utility namespace used by other testing namespaces.
      This namespace itself does not contain any tests"}
scae-library.sample-inputs)

;;================== Core and Tokeniser ========================================
(def sample-ccal-rulebook-string (slurp "resources/sample-ccal-rulebook-1.json"))

(def sample-ccal-code-4-string (slurp "resources/sample-ccal-code-4.txt"))

(def sample-ccal-code-4-rulebook-1-suggestion (lazy-seq ["Instead of calling funcB as the first parameter of funcA, try calling funcC"]))

(def tokenised-code-sample-ccal-code-4 (lazy-seq [{:token-key :INT, :token-value "integer", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :ID, :token-value "funcB", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :LBR, :token-value "(", :token-type :token}
                                                  {:token-key :RBR, :token-value ")", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :IS, :token-value "is", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :VAR, :token-value "var", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :ID, :token-value "i", :token-type :token}
                                                  {:token-key :COLON, :token-value ":", :token-type :token}
                                                  {:token-key :INT, :token-value "integer", :token-type :token}
                                                  {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :BEGIN, :token-value "begin", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :ID, :token-value "i", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :ASSIGN, :token-value "=", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :NUM, :token-value "2", :token-type :token}
                                                  {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :RET, :token-value "return", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :LBR, :token-value "(", :token-type :token}
                                                  {:token-key :ID, :token-value "i", :token-type :token}
                                                  {:token-key :RBR, :token-value ")", :token-type :token}
                                                  {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :END, :token-value "end", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :INT, :token-value "integer", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :ID, :token-value "funcA", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :LBR, :token-value "(", :token-type :token}
                                                  {:token-key :ID, :token-value "y", :token-type :token}
                                                  {:token-key :COLON, :token-value ":", :token-type :token}
                                                  {:token-key :INT, :token-value "integer", :token-type :token}
                                                  {:token-key :RBR, :token-value ")", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :IS, :token-value "is", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :VAR, :token-value "var", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :ID, :token-value "i", :token-type :token}
                                                  {:token-key :COLON, :token-value ":", :token-type :token}
                                                  {:token-key :INT, :token-value "integer", :token-type :token}
                                                  {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :BEGIN, :token-value "begin", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :ID, :token-value "i", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :ASSIGN, :token-value "=", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :NUM, :token-value "3", :token-type :token}
                                                  {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :RET, :token-value "return", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :LBR, :token-value "(", :token-type :token}
                                                  {:token-key :ID, :token-value "y", :token-type :token}
                                                  {:token-key :RBR, :token-value ")", :token-type :token}
                                                  {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :END, :token-value "end", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :MAIN, :token-value "main", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :BEGIN, :token-value "begin", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :ID, :token-value "funcA", :token-type :token}
                                                  {:token-key :SPACE, :token-value " ", :token-type :skip}
                                                  {:token-key :LBR, :token-value "(", :token-type :token}
                                                  {:token-key :ID, :token-value "funcB", :token-type :token}
                                                  {:token-key :RBR, :token-value ")", :token-type :token}
                                                  {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                  {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                                  {:token-key :END, :token-value "end", :token-type :token}]))


(def sample-ccal-rulebook-1-token-and-skip-definition
  {:token {:NEGATE     "~",
           :COLON      ":",
           :ID         "(?!var|const|return|integer|boolean|void|main|if|else|true|false|while|begin|end|is|skip)(?:(?:[a-zA-Z])+(?:[0-9]|_|[a-zA-Z])*)",
           :LBR        "\\(",
           :GT         ">",
           :MINUS_SIGN "-",
           :TRUE       "true",
           :FALSE      "false",
           :BOOL       "boolean",
           :WHILE      "while",
           :BEGIN      "begin",
           :AND        "&&",
           :COMMA      ",",
           :PLUS_SIGN  "\\+",
           :GTE        ">=",
           :OR         "\\|\\|",
           :NUM        "(?:0|(?:(?:[1-9])+(?:[0-9])*))",
           :IS         "is",
           :SKI        "skip",
           :RET        "return",
           :SEMI_COLON ";",
           :NOT_EQUALs "!=",
           :VOID       "void",
           :LTE        "<=",
           :CONST      "const",
           :IF         "if",
           :ASSIGN     "=",
           :MAIN       "main",
           :END        "end",
           :LT         "<",
           :RBR        "\\)",
           :EQUALS     "==",
           :ELSE       "else",
           :INT        "integer",
           :VAR        "var"},
   :skip  {:SPACE           " ",
           :TAB             "\t",
           :NEWLINE         "\n",
           :CARRIAGE_RETURN "\r",
           :FORM_FEED       "\f"}})

(def sample-ccal-rulebook-1-token-definition-with-regex
  {:tokens
   {:NEGATE          {:regex "~", :type :token},
    :COLON           {:regex ":", :type :token},
    :ID              {:regex "(?!var|const|return|integer|boolean|void|main|if|else|true|false|while|begin|end|is|skip)(?:(?:[a-zA-Z])+(?:[0-9]|_|[a-zA-Z])*)", :type :token},
    :LBR             {:regex "\\(", :type :token},
    :GT              {:regex ">", :type :token},
    :MINUS_SIGN      {:regex "-", :type :token},
    :TRUE            {:regex "true", :type :token},
    :FALSE           {:regex "false", :type :token},
    :CARRIAGE_RETURN {:regex "\r", :type :skip},
    :BOOL            {:regex "boolean", :type :token},
    :WHILE           {:regex "while", :type :token},
    :BEGIN           {:regex "begin", :type :token},
    :AND             {:regex "&&", :type :token},
    :COMMA           {:regex ",", :type :token},
    :PLUS_SIGN       {:regex "\\+", :type :token},
    :GTE             {:regex ">=", :type :token},
    :OR              {:regex "\\|\\|", :type :token},
    :NUM             {:regex "(?:0|(?:(?:[1-9])+(?:[0-9])*))", :type :token},
    :IS              {:regex "is", :type :token},
    :SKI             {:regex "skip", :type :token},
    :RET             {:regex "return", :type :token},
    :SEMI_COLON      {:regex ";", :type :token},
    :NOT_EQUALs      {:regex "!=", :type :token},
    :VOID            {:regex "void", :type :token},
    :LTE             {:regex "<=", :type :token},
    :NEWLINE         {:regex "\n", :type :skip},
    :CONST           {:regex "const", :type :token},
    :FORM_FEED       {:regex "\f", :type :skip},
    :IF              {:regex "if", :type :token},
    :ASSIGN          {:regex "=", :type :token},
    :MAIN            {:regex "main", :type :token},
    :END             {:regex "end", :type :token},
    :LT              {:regex "<", :type :token},
    :RBR             {:regex "\\)", :type :token},
    :EQUALS          {:regex "==", :type :token},
    :SPACE           {:regex " ", :type :skip},
    :ELSE            {:regex "else", :type :token},
    :TAB             {:regex "\t", :type :skip},
    :INT             {:regex "integer", :type :token},
    :VAR             {:regex "var", :type :token}}})


;;================== End of core and tokeniser =================================

;;================== Abstract Syntax Tree ======================================
(def ast-sample-ccal-code-4 {:parsed-node-name "scae-program",
                             :parsed-node-result
                                               '({:parsed-node-name "decl-list", :parsed-node-result []}
                                                 {:parsed-node-name "function-list",
                                                  :parsed-node-result
                                                                    ({:parsed-node-name "function",
                                                                      :parsed-node-result
                                                                                        ({:parsed-node-name "data-type",
                                                                                          :parsed-node-result
                                                                                                            ({:token-key :INT, :token-value "integer", :token-type :token})}
                                                                                          {:parsed-node-name "identifier",
                                                                                           :parsed-node-result
                                                                                                             ({:token-key :ID, :token-value "funcB", :token-type :token})}
                                                                                          {:token-key :LBR, :token-value "(", :token-type :token}
                                                                                          {:parsed-node-name "parameter-list", :parsed-node-result []}
                                                                                          {:token-key :RBR, :token-value ")", :token-type :token}
                                                                                          {:token-key :IS, :token-value "is", :token-type :token}
                                                                                          {:st-func-calls
                                                                                           [(fn [ast-entry]
                                                                                              (let [name (:token-value (nth (:parsed-node-result (nth ast-entry 1)) 0))
                                                                                                    value (:token-value (nth (:parsed-node-result (nth ast-entry 0)) 0))]
                                                                                                (scae-library.symbol-table/set-current-scope-name! name)
                                                                                                (scae-library.symbol-table/add-to-scope
                                                                                                  name
                                                                                                  {:symbol-name name :symbol-value value}
                                                                                                  scae-library.symbol-table/GLOBAL_SCOPE_NAME)))]}
                                                                                          {:parsed-node-name "decl-list",
                                                                                           :parsed-node-result
                                                                                                             ({:parsed-node-name "decl",
                                                                                                               :parsed-node-result
                                                                                                                                 ({:parsed-node-name "var-decl",
                                                                                                                                   :parsed-node-result
                                                                                                                                                     ({:token-key :VAR, :token-value "var", :token-type :token}
                                                                                                                                                       {:parsed-node-name "identifier",
                                                                                                                                                        :parsed-node-result
                                                                                                                                                                          ({:token-key :ID, :token-value "i", :token-type :token})}
                                                                                                                                                       {:token-key :COLON, :token-value ":", :token-type :token}
                                                                                                                                                       {:parsed-node-name "data-type",
                                                                                                                                                        :parsed-node-result
                                                                                                                                                                          ({:token-key :INT,
                                                                                                                                                                            :token-value "integer",
                                                                                                                                                                            :token-type :token})}
                                                                                                                                                       {:st-func-calls
                                                                                                                                                        (fn [ast-entry]
                                                                                                                                                          (let [name (:token-value (nth (:parsed-node-result (nth ast-entry 1)) 0))
                                                                                                                                                                value (:token-value (nth (:parsed-node-result (nth ast-entry 3)) 0))]
                                                                                                                                                            (scae-library.symbol-table/add-to-scope
                                                                                                                                                              name
                                                                                                                                                              {:symbol-name name :symbol-value value})))})})}
                                                                                                               {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                                                                               {:parsed-node-name "decl-list", :parsed-node-result []})}
                                                                                          {:token-key :BEGIN, :token-value "begin", :token-type :token}
                                                                                          {:parsed-node-name "statement-block",
                                                                                           :parsed-node-result
                                                                                                             ({:parsed-node-name "statement",
                                                                                                               :parsed-node-result
                                                                                                                                 ({:parsed-node-name "identifier",
                                                                                                                                   :parsed-node-result
                                                                                                                                                     ({:token-key :ID, :token-value "i", :token-type :token})}
                                                                                                                                   {:parsed-node-name "statement-prime",
                                                                                                                                    :parsed-node-result
                                                                                                                                                      ({:token-key :ASSIGN, :token-value "=", :token-type :token}
                                                                                                                                                        {:parsed-node-name "expression",
                                                                                                                                                         :parsed-node-result
                                                                                                                                                                           ({:parsed-node-name "optional-lbr",
                                                                                                                                                                             :parsed-node-result []}
                                                                                                                                                                             {:parsed-node-name "fragment",
                                                                                                                                                                              :parsed-node-result
                                                                                                                                                                                                ({:parsed-node-name "id-or-fragment-delta",
                                                                                                                                                                                                  :parsed-node-result
                                                                                                                                                                                                                    ({:parsed-node-name "fragment-delta",
                                                                                                                                                                                                                      :parsed-node-result
                                                                                                                                                                                                                                        ({:parsed-node-name "number",
                                                                                                                                                                                                                                          :parsed-node-result
                                                                                                                                                                                                                                                            ({:token-key :NUM,
                                                                                                                                                                                                                                                              :token-value "2",
                                                                                                                                                                                                                                                              :token-type :token})})})}
                                                                                                                                                                                                  {:parsed-node-name "optional-expression-beta",
                                                                                                                                                                                                   :parsed-node-result []}
                                                                                                                                                                                                  {:parsed-node-name "fragment-prime",
                                                                                                                                                                                                   :parsed-node-result []})}
                                                                                                                                                                             {:parsed-node-name "optional-rbr",
                                                                                                                                                                              :parsed-node-result []})}
                                                                                                                                                        {:token-key :SEMI_COLON,
                                                                                                                                                         :token-value ";",
                                                                                                                                                         :token-type :token})})}
                                                                                                               {:parsed-node-name "statement-block", :parsed-node-result []})}
                                                                                          {:token-key :RET, :token-value "return", :token-type :token}
                                                                                          {:parsed-node-name "optional-lbr",
                                                                                           :parsed-node-result
                                                                                                             ({:token-key :LBR, :token-value "(", :token-type :token})}
                                                                                          {:parsed-node-name "optional-expression",
                                                                                           :parsed-node-result
                                                                                                             ({:parsed-node-name "expression",
                                                                                                               :parsed-node-result
                                                                                                                                 ({:parsed-node-name "optional-lbr", :parsed-node-result []}
                                                                                                                                   {:parsed-node-name "fragment",
                                                                                                                                    :parsed-node-result
                                                                                                                                                      ({:parsed-node-name "id-or-fragment-delta",
                                                                                                                                                        :parsed-node-result
                                                                                                                                                                          ({:parsed-node-name "identifier",
                                                                                                                                                                            :parsed-node-result
                                                                                                                                                                                              ({:token-key :ID,
                                                                                                                                                                                                :token-value "i",
                                                                                                                                                                                                :token-type :token})})}
                                                                                                                                                        {:parsed-node-name "optional-expression-beta",
                                                                                                                                                         :parsed-node-result []}
                                                                                                                                                        {:parsed-node-name "fragment-prime",
                                                                                                                                                         :parsed-node-result []})}
                                                                                                                                   {:parsed-node-name "optional-rbr",
                                                                                                                                    :parsed-node-result
                                                                                                                                                      ({:token-key :RBR,
                                                                                                                                                        :token-value ")",
                                                                                                                                                        :token-type :token})})})}
                                                                                          {:parsed-node-name "optional-rbr", :parsed-node-result []}
                                                                                          {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                                                          {:token-key :END, :token-value "end", :token-type :token}
                                                                                          {:st-func-calls
                                                                                           [(fn [ast-entry]
                                                                                              (scae-library.symbol-table/set-current-scope-name-to-previous!))]})}
                                                                      {:parsed-node-name "function-list",
                                                                       :parsed-node-result
                                                                                         ({:parsed-node-name "function",
                                                                                           :parsed-node-result
                                                                                                             ({:parsed-node-name "data-type",
                                                                                                               :parsed-node-result
                                                                                                                                 ({:token-key :INT,
                                                                                                                                   :token-value "integer",
                                                                                                                                   :token-type :token})}
                                                                                                               {:parsed-node-name "identifier",
                                                                                                                :parsed-node-result
                                                                                                                                  ({:token-key :ID, :token-value "funcA", :token-type :token})}
                                                                                                               {:token-key :LBR, :token-value "(", :token-type :token}
                                                                                                               {:parsed-node-name "parameter-list",
                                                                                                                :parsed-node-result
                                                                                                                                  ({:parsed-node-name "nemp-parameter-list",
                                                                                                                                    :parsed-node-result
                                                                                                                                                      ({:parsed-node-name "identifier",
                                                                                                                                                        :parsed-node-result
                                                                                                                                                                          ({:token-key :ID, :token-value "y", :token-type :token})}
                                                                                                                                                        {:token-key :COLON, :token-value ":", :token-type :token}
                                                                                                                                                        {:parsed-node-name "data-type",
                                                                                                                                                         :parsed-node-result
                                                                                                                                                                           ({:token-key :INT,
                                                                                                                                                                             :token-value "integer",
                                                                                                                                                                             :token-type :token})}
                                                                                                                                                        {:parsed-node-name "nemp-parameter-list-prime",
                                                                                                                                                         :parsed-node-result []})})}
                                                                                                               {:token-key :RBR, :token-value ")", :token-type :token}
                                                                                                               {:token-key :IS, :token-value "is", :token-type :token}
                                                                                                               {:st-func-calls
                                                                                                                [(fn [ast-entry]
                                                                                                                   (let [name (:token-value (nth (:parsed-node-result (nth ast-entry 1)) 0))
                                                                                                                         value (:token-value (nth (:parsed-node-result (nth ast-entry 0)) 0))]
                                                                                                                     (scae-library.symbol-table/set-current-scope-name! name)
                                                                                                                     (scae-library.symbol-table/add-to-scope
                                                                                                                       name
                                                                                                                       {:symbol-name name :symbol-value value}
                                                                                                                       scae-library.symbol-table/GLOBAL_SCOPE_NAME)))]}
                                                                                                               {:parsed-node-name "decl-list",
                                                                                                                :parsed-node-result
                                                                                                                                  ({:parsed-node-name "decl",
                                                                                                                                    :parsed-node-result
                                                                                                                                                      ({:parsed-node-name "var-decl",
                                                                                                                                                        :parsed-node-result
                                                                                                                                                                          ({:token-key :VAR, :token-value "var", :token-type :token}
                                                                                                                                                                            {:parsed-node-name "identifier",
                                                                                                                                                                             :parsed-node-result
                                                                                                                                                                                               ({:token-key :ID,
                                                                                                                                                                                                 :token-value "i",
                                                                                                                                                                                                 :token-type :token})}
                                                                                                                                                                            {:token-key :COLON, :token-value ":", :token-type :token}
                                                                                                                                                                            {:parsed-node-name "data-type",
                                                                                                                                                                             :parsed-node-result
                                                                                                                                                                                               ({:token-key :INT,
                                                                                                                                                                                                 :token-value "integer",
                                                                                                                                                                                                 :token-type :token})}
                                                                                                                                                                            {:st-func-calls
                                                                                                                                                                             [(fn [ast-entry]
                                                                                                                                                                                (let
                                                                                                                                                                                  [name (:token-value (nth (:parsed-node-result (nth ast-entry 1)) 0))
                                                                                                                                                                                   value (:token-value (nth (:parsed-node-result (nth ast-entry 3)) 0))]
                                                                                                                                                                                  (scae-library.symbol-table/add-to-scope
                                                                                                                                                                                    name
                                                                                                                                                                                    {:symbol-name name :symbol-value value})))]})})}
                                                                                                                                    {:token-key :SEMI_COLON,
                                                                                                                                     :token-value ";",
                                                                                                                                     :token-type :token}
                                                                                                                                    {:parsed-node-name "decl-list", :parsed-node-result []})}
                                                                                                               {:token-key :BEGIN, :token-value "begin", :token-type :token}
                                                                                                               {:parsed-node-name "statement-block",
                                                                                                                :parsed-node-result
                                                                                                                                  ({:parsed-node-name "statement",
                                                                                                                                    :parsed-node-result
                                                                                                                                                      ({:parsed-node-name "identifier",
                                                                                                                                                        :parsed-node-result
                                                                                                                                                                          ({:token-key :ID, :token-value "i", :token-type :token})}
                                                                                                                                                        {:parsed-node-name "statement-prime",
                                                                                                                                                         :parsed-node-result
                                                                                                                                                                           ({:token-key :ASSIGN,
                                                                                                                                                                             :token-value "=",
                                                                                                                                                                             :token-type :token}
                                                                                                                                                                             {:parsed-node-name "expression",
                                                                                                                                                                              :parsed-node-result
                                                                                                                                                                                                ({:parsed-node-name "optional-lbr",
                                                                                                                                                                                                  :parsed-node-result []}
                                                                                                                                                                                                  {:parsed-node-name "fragment",
                                                                                                                                                                                                   :parsed-node-result
                                                                                                                                                                                                                     ({:parsed-node-name "id-or-fragment-delta",
                                                                                                                                                                                                                       :parsed-node-result
                                                                                                                                                                                                                                         ({:parsed-node-name "fragment-delta",
                                                                                                                                                                                                                                           :parsed-node-result
                                                                                                                                                                                                                                                             ({:parsed-node-name "number",
                                                                                                                                                                                                                                                               :parsed-node-result
                                                                                                                                                                                                                                                                                 ({:token-key :NUM,
                                                                                                                                                                                                                                                                                   :token-value "3",
                                                                                                                                                                                                                                                                                   :token-type :token})})})}
                                                                                                                                                                                                                       {:parsed-node-name "optional-expression-beta",
                                                                                                                                                                                                                        :parsed-node-result []}
                                                                                                                                                                                                                       {:parsed-node-name "fragment-prime",
                                                                                                                                                                                                                        :parsed-node-result []})}
                                                                                                                                                                                                  {:parsed-node-name "optional-rbr",
                                                                                                                                                                                                   :parsed-node-result []})}
                                                                                                                                                                             {:token-key :SEMI_COLON,
                                                                                                                                                                              :token-value ";",
                                                                                                                                                                              :token-type :token})})}
                                                                                                                                    {:parsed-node-name "statement-block",
                                                                                                                                     :parsed-node-result []})}
                                                                                                               {:token-key :RET, :token-value "return", :token-type :token}
                                                                                                               {:parsed-node-name "optional-lbr",
                                                                                                                :parsed-node-result
                                                                                                                                  ({:token-key :LBR, :token-value "(", :token-type :token})}
                                                                                                               {:parsed-node-name "optional-expression",
                                                                                                                :parsed-node-result
                                                                                                                                  ({:parsed-node-name "expression",
                                                                                                                                    :parsed-node-result
                                                                                                                                                      ({:parsed-node-name "optional-lbr", :parsed-node-result []}
                                                                                                                                                        {:parsed-node-name "fragment",
                                                                                                                                                         :parsed-node-result
                                                                                                                                                                           ({:parsed-node-name "id-or-fragment-delta",
                                                                                                                                                                             :parsed-node-result
                                                                                                                                                                                               ({:parsed-node-name "identifier",
                                                                                                                                                                                                 :parsed-node-result
                                                                                                                                                                                                                   ({:token-key :ID,
                                                                                                                                                                                                                     :token-value "y",
                                                                                                                                                                                                                     :token-type :token})})}
                                                                                                                                                                             {:parsed-node-name "optional-expression-beta",
                                                                                                                                                                              :parsed-node-result []}
                                                                                                                                                                             {:parsed-node-name "fragment-prime",
                                                                                                                                                                              :parsed-node-result []})}
                                                                                                                                                        {:parsed-node-name "optional-rbr",
                                                                                                                                                         :parsed-node-result
                                                                                                                                                                           ({:token-key :RBR,
                                                                                                                                                                             :token-value ")",
                                                                                                                                                                             :token-type :token})})})}
                                                                                                               {:parsed-node-name "optional-rbr", :parsed-node-result []}
                                                                                                               {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                                                                                               {:token-key :END, :token-value "end", :token-type :token}
                                                                                                               {:st-func-calls
                                                                                                                [(fn [ast-entry]
                                                                                                                   (scae-library.symbol-table/set-current-scope-name-to-previous!))]})}
                                                                                           {:parsed-node-name "function-list", :parsed-node-result []})})}
                                                 {:parsed-node-name "main",
                                                  :parsed-node-result
                                                                    ({:st-func-calls
                                                                      [(fn [ast-entry]
                                                                         (scae-library.symbol-table/set-current-scope-name! "main"))]}
                                                                      {:token-key :MAIN, :token-value "main", :token-type :token}
                                                                      {:token-key :BEGIN, :token-value "begin", :token-type :token}
                                                                      {:parsed-node-name "decl-list", :parsed-node-result []}
                                                                      {:parsed-node-name "statement-block",
                                                                       :parsed-node-result
                                                                                         ({:parsed-node-name "statement",
                                                                                           :parsed-node-result
                                                                                                             ({:parsed-node-name "identifier",
                                                                                                               :parsed-node-result
                                                                                                                                 ({:token-key :ID, :token-value "funcA", :token-type :token})}
                                                                                                               {:parsed-node-name "statement-prime",
                                                                                                                :parsed-node-result
                                                                                                                                  ({:token-key :LBR, :token-value "(", :token-type :token}
                                                                                                                                    {:parsed-node-name "arg-list",
                                                                                                                                     :parsed-node-result
                                                                                                                                                       ({:parsed-node-name "nemp-arg-list",
                                                                                                                                                         :parsed-node-result
                                                                                                                                                                           ({:parsed-node-name "identifier",
                                                                                                                                                                             :parsed-node-result
                                                                                                                                                                                               ({:token-key :ID,
                                                                                                                                                                                                 :token-value "funcB",
                                                                                                                                                                                                 :token-type :token})}
                                                                                                                                                                             {:parsed-node-name "nemp-arg-list-prime",
                                                                                                                                                                              :parsed-node-result []})})}
                                                                                                                                    {:token-key :RBR, :token-value ")", :token-type :token}
                                                                                                                                    {:token-key :SEMI_COLON,
                                                                                                                                     :token-value ";",
                                                                                                                                     :token-type :token})})}
                                                                                           {:parsed-node-name "statement-block", :parsed-node-result []})}
                                                                      {:token-key :END, :token-value "end", :token-type :token}
                                                                      {:st-func-calls
                                                                       [(fn [ast-entry]
                                                                          (scae-library.symbol-table/set-current-scope-name-to-previous!))]})})})

(def count-tokens-data-set
  {:four-tokens '({:parsed-node-name   "statement",
                   :parsed-node-result ({:parsed-node-name   "identifier",
                                         :parsed-node-result ({:token-key   :ID,
                                                               :token-value "i",
                                                               :token-type  :token})}
                                         {:parsed-node-name   "statement-prime",
                                          :parsed-node-result ({:token-key   :ASSIGN,
                                                                :token-value "=",
                                                                :token-type  :token}
                                                                {:parsed-node-name   "expression",
                                                                 :parsed-node-result ({:parsed-node-name   "optional-lbr",
                                                                                       :parsed-node-result []}
                                                                                       {:parsed-node-name   "fragment",
                                                                                        :parsed-node-result ({:parsed-node-name   "id-or-fragment-delta",
                                                                                                              :parsed-node-result ({:parsed-node-name   "fragment-delta",
                                                                                                                                    :parsed-node-result ({:parsed-node-name   "number",
                                                                                                                                                          :parsed-node-result ({:token-key   :NUM,
                                                                                                                                                                                :token-value "2",
                                                                                                                                                                                :token-type  :token})})})}
                                                                                                              {:parsed-node-name   "optional-expression-beta",
                                                                                                               :parsed-node-result []}
                                                                                                              {:parsed-node-name   "fragment-prime",
                                                                                                               :parsed-node-result []})}
                                                                                       {:parsed-node-name "optional-rbr",
                                                                                        :parsed-node-result []})}
                                                                {:token-key :SEMI_COLON,
                                                                 :token-value ";",
                                                                 :token-type :token})})}
                   {:parsed-node-name "statement-block",
                    :parsed-node-result []})

   :zero-tokens '({:parsed-node-name   "statement-block",
                   :parsed-node-result []})})


;;parse-single-option-test-data-set
(def parse-single-option-data-set
  {:empty-option-list {:tokens '({:token-key :END,
                                  :token-value "end",
                                  :token-type :token})

                       :option-vec '()
                       :expected-result []}

   :epsilon-transition {:tokens  '({:token-key :ID,
                                   :token-value "funcB",
                                   :token-type :token}
                                   {:token-key :RBR,
                                    :token-value ")",
                                    :token-type :token}
                                   {:token-key :SEMI_COLON,
                                    :token-value ";",
                                    :token-type :token}
                                   {:token-key :END,
                                    :token-value "end",
                                    :token-type :token})
                        :option-vec [[]]
                        :expected-result []}

   :one-in-option-vec-multiple-tokens {:tokens  '({:token-key :LBR,
                                                  :token-value "(",
                                                  :token-type :token}
                                                  {:token-key :ID,
                                                   :token-value "funcB",
                                                   :token-type :token}
                                                  {:token-key :RBR,
                                                   :token-value ")",
                                                   :token-type :token}
                                                  {:token-key :SEMI_COLON,
                                                   :token-value ";",
                                                   :token-type :token}
                                                  {:token-key :END,
                                                   :token-value "end",
                                                   :token-type :token})

                                       :option-vec '((statement-prime))
                                       :expected-result  '({:parsed-node-name "statement-prime",
                                                            :parsed-node-result ({:token-key :LBR,
                                                                                   :token-value "(",
                                                                                   :token-type :token}
                                                                                   {:parsed-node-name "arg-list",
                                                                                    :parsed-node-result ({:parsed-node-name "nemp-arg-list",
                                                                                                           :parsed-node-result ({:parsed-node-name "identifier",
                                                                                                                                  :parsed-node-result ({:token-key :ID,
                                                                                                                                                         :token-value "funcB",
                                                                                                                                                         :token-type :token})}
                                                                                                                                  {:parsed-node-name "nemp-arg-list-prime",
                                                                                                                                   :parsed-node-result []})})}
                                                                                   {:token-key :RBR,
                                                                                    :token-value ")",
                                                                                    :token-type :token}
                                                                                   {:token-key :SEMI_COLON,
                                                                                    :token-value ";",
                                                                                    :token-type :token})})}

   :multiple-in-option-vec {:tokens  '({:token-key :LBR,
                                        :token-value "(",
                                        :token-type :token}
                                        {:token-key :ID,
                                         :token-value "funcB",
                                         :token-type :token}
                                        {:token-key :RBR,
                                         :token-value ")",
                                         :token-type :token}
                                        {:token-key :SEMI_COLON,
                                         :token-value ";",
                                         :token-type :token}
                                        {:token-key :END,
                                         :token-value "end",
                                         :token-type :token})

                            :option-vec  [:LBR '(arg-list) :RBR :SEMI_COLON]

                            :expected-result  '({:token-key :LBR,
                                                 :token-value "(",
                                                 :token-type :token}
                                                 {:parsed-node-name "arg-list",
                                                  :parsed-node-result ({:parsed-node-name "nemp-arg-list",
                                                                         :parsed-node-result ({:parsed-node-name "identifier",
                                                                                                :parsed-node-result ({:token-key :ID,
                                                                                                                       :token-value "funcB",
                                                                                                                       :token-type :token})}
                                                                                                {:parsed-node-name "nemp-arg-list-prime",
                                                                                                 :parsed-node-result []})})}
                                                 {:token-key :RBR,
                                                  :token-value ")",
                                                  :token-type :token}
                                                 {:token-key :SEMI_COLON,
                                                  :token-value ";",
                                                  :token-type :token})}

   :one-in-option-vec-one-in-tokens {:tokens  '({:token-key :END,
                                             :token-value "end",
                                             :token-type :token})
                                     :option-vec  '((statement-block))
                                     :expected-result  '({:parsed-node-name "statement-block",
                                                          :parsed-node-result []})}})

;;==============================================================================