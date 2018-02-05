(ns scae-library.core)

(defn foo
  "A dummy function to see if the library can be called from external project
  \nExpects a map in the form {:code \"\"
                             :rulebook \"\"}"
  [request]
  (str "Value received by SCAE-Library: " request
       "\nCode section: " (:code request)
       "\nRulebook section: " (:rulebook request)))
