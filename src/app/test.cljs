(ns app.test
  (:require  [cljs.test :refer :all :include-macros true]
             [app.helpers :as h]))



(deftest test-scramble
  (testing "Scramble"
    (is (= (h/contain-keys? {:a 1 :b 2 :c 3} [:a :b]) true))
    (is (= (h/contain-keys? {:a 1 :b 2 :c 3} [:a :d]) false))
    (is (= (h/scramble? "rekqodlw" "world") true))
    (is (= (h/scramble? "cedewaraaossoqqyt" "codewars") true))
    (is (= (h/scramble? "katas"  "steak") false))))
