(ns scae-library.node-ops)

;;Helper function used to quickly make different kind of type checks
(defn entry-type-check?
  [ast-entry key]
  (if (get ast-entry key)
    true
    nil))

(defn node?
  [ast-entry]
  (entry-type-check? ast-entry :parsed-node-name))

(defn token?
  [ast-entry]
  (entry-type-check? ast-entry :token-key))

(defn st-func-calls?
  [ast-entry]
  (entry-type-check? ast-entry :st-func-calls))

;;todo implement tracking of line numbers. Implemented check below in preparation
(defn line-number?
  [ast-entry]
  (entry-type-check? ast-entry :line-number))

(defn get-parsed-node-result
  "This returns the parsed node result. If the parsed node result is a string,
  it reads the string and returns the eval'd result. This is due to elsewhere
  in the program, the style analyser, having to quote the parsed node result so
  that internal hash-maps would not try to be evaluated as a function if they
  were at the head of a list and eval were called on them"
  [ast-node]
  (let [parsed-node-result (:parsed-node-result ast-node)]
    (if (string? parsed-node-result)
      ;;If string read it, eval it, then return it
      (eval (read-string parsed-node-result))
      ;;else it is not a string, so can return it straight away
      parsed-node-result)))

(defn get-node-name
  [ast-entry]
  (:parsed-node-name ast-entry))

(defn get-node-children
  [ast-entry]
  (get-parsed-node-result ast-entry))


(defn get-nth-child
  "Get the nth child of the ast node. Uses zero indexing (0 is first position).
  Ignores symbol table function calls and line-numbers"
  [ast-node n]
  (let [children (for [entry (get-parsed-node-result ast-node)
                       ;;only compile list of nodes and tokens
                       ;;(essentially ignore st-func-calls and line numbers)
                       :when (or (node? entry)
                                 (token? entry))]
                   entry)]
    ;;return nth element, if it doesn't exist return nil
    (nth children n nil))
  )

(defn get-nth-named-child
  "Retrieve the nth instance of the target child. Uses zero indexing "
  [ast-node n child-name]
  (let [matching-children
        (for [entry (get-parsed-node-result ast-node)
              :when (or
                      ;;check for specific child of type node
                      (= child-name
                         (:parsed-node-name entry))
                      ;;check for specific child of type token
                      (= child-name
                         (:token-key entry)))]
          entry)]
    ;;check that there are at least n matches. If true return nth, if not return nil
    (if (<= n (count matching-children))
      (nth matching-children n nil)
      nil)))

(defn node-contains-top-layer-child?
  [ast-node child-name]
  ;;if a single (first) instance of child exists, return true, else nil
  (if (get-nth-named-child ast-node 0 child-name)
    true
    nil))

;;todo are the below as useful as I think they are? Because child-name would be "function", not "funcA"
(defn node-contains-nested-child?
  [ast-node child-name]
  ;;if check for result on top layer
  ;;else loop through and recursive call for each element that is a node
  (if (node-contains-top-layer-child? ast-node child-name)
    true
    (some true?
          (for [entry (get-parsed-node-result ast-node)
                :when (node? entry)]
            (node-contains-nested-child? entry child-name)))))

(defn sub-node-contains-top-layer-child?
  [ast-node sub-node-name child-name]
  (some true?
        (for [entry (get-parsed-node-result ast-node)
              :when (= sub-node-name
                       (:parsed-node-name entry))]
          (node-contains-top-layer-child? entry child-name))))

(defn sub-node-contains-nested-child?
  [ast-node sub-node-name child-name]
  (some true?
        (for [entry (get-parsed-node-result ast-node)
              :when (= sub-node-name
                       (:parsed-node-name entry))]
          (node-contains-nested-child? entry child-name))))