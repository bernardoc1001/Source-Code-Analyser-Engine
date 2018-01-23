(ns scae-website.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [scae-website.core-test]))

(doo-tests 'scae-website.core-test)
