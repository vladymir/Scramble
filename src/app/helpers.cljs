(ns app.helpers)

(defn contain-keys?
  "This functions takes a freq-map and a list of keys and return true
  if every key in ks is present in m."
  [m ks]
  (every? #(contains? m %) ks))

(defn solve
  "This function takes two freq-maps of strings and return true if
  it is possible to scramble or false otherwise."
  [fs1 fs2]
  (if (contain-keys? fs1 (keys fs2))
    (every? #(>= (fs1 %) (fs2 %)) (keys fs2))
    false))


(defn solve2
  "This function takes two freq-maps of strings and return true if
  it is possible to scramble or false otherwise."
  [fs1 fs2]
  (and (contain-keys? fs1 (keys fs2))
       (every? #(>= (fs1 %) (fs2 %)) (keys fs2))))

(defn scramble?
  "This function return true if the strings scramble."
  [str1 str2]
  (let [f1 (frequencies str1)
        f2 (frequencies str2)]
    (solve f1 f2)))
