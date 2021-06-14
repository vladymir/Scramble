(ns app.handlers
  (:require [app.core :refer results]))


(rf/reg-event-fx
 :scramble
 (fn [_ [_ result]]
   (let [str1 (:str1 result)
         str2 (:str2 result)
         scr (str (h/scramble? str1 str2))
         res (-> result
                 (assoc :result scr)
                 (assoc :key (random-uuid)))]
     (swap! results update-in [:results] conj res))))


(rf/reg-event-db
 :str1
 (fn [_ [_ value]]
   (swap! results assoc :str1 value)))


(rf/reg-event-db
 :str2
 (fn [_ [_ value]]
   (swap! results assoc :str2 value)))
