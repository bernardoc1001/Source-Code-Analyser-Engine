(ns scae-library.sample-inputs)

(def sample-rulebook-json (slurp "resources/sample-rulebook.json"))

(def sample-code-1-txt (slurp "resources/sample-code-1.txt"))

(def tokenised-sample-code-1 (seq [{:token-key :ID, :token-value "var", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "i", :token-type :token}
                                   {:token-key :COLON, :token-value ":", :token-type :token}
                                   {:token-key :ID, :token-value "integer", :token-type :token}
                                   {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :ID, :token-value "integer", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "test_fn", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :LBR, :token-value "(", :token-type :token}
                                   {:token-key :ID, :token-value "x", :token-type :token}
                                   {:token-key :COLON, :token-value ":", :token-type :token}
                                   {:token-key :ID, :token-value "integer", :token-type :token}
                                   {:token-key :RBR, :token-value ")", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "is", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "var", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "i", :token-type :token}
                                   {:token-key :COLON, :token-value ":", :token-type :token}
                                   {:token-key :ID, :token-value "integer", :token-type :token}
                                   {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :ID, :token-value "begin", :token-type :token}
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
                                   {:token-key :ID, :token-value "return", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :LBR, :token-value "(", :token-type :token}
                                   {:token-key :ID, :token-value "x", :token-type :token}
                                   {:token-key :RBR, :token-value ")", :token-type :token}
                                   {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :ID, :token-value "end", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :ID, :token-value "main", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :ID, :token-value "begin", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "var", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "i", :token-type :token}
                                   {:token-key :COLON, :token-value ":", :token-type :token}
                                   {:token-key :ID, :token-value "integer", :token-type :token}
                                   {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "i", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ASSIGN, :token-value "=", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :NUM, :token-value "1", :token-type :token}
                                   {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "i", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ASSIGN, :token-value "=", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :ID, :token-value "test_fn", :token-type :token}
                                   {:token-key :SPACE, :token-value " ", :token-type :skip}
                                   {:token-key :LBR, :token-value "(", :token-type :token}
                                   {:token-key :ID, :token-value "i", :token-type :token}
                                   {:token-key :RBR, :token-value ")", :token-type :token}
                                   {:token-key :SEMI_COLON, :token-value ";", :token-type :token}
                                   {:token-key :NEWLINE, :token-value "\n", :token-type :skip}
                                   {:token-key :ID, :token-value "end", :token-type :token}]))

(def sample-token-definitions-output
  {:tokens {:NEGATE          {:regex "~", :type :token},
            :COLON           {:regex ":", :type :token},
            :ID              {:regex "(?:[a-zA-Z])+(?:[0-9]|_|[a-zA-Z])*", :type :token},
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

(def sample-insert-token-type-input
  {:token {:NEGATE     "~",
           :COLON      ":",
           :ID         "(?:[a-zA-Z])+(?:[0-9]|_|[a-zA-Z])*",
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
