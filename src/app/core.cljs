(ns app.core
  (:require [reagent.core :as r]
            [app.helpers :as h]
            [re-frame.core :as rf]))


(def results (r/atom {:results []}))

(defn row [c1 c2 c3]
  [:div {:class "row" :key (random-uuid)}
   [:div {:class "column"}
    [:p c1]]
   [:div {:class "column"}
    [:p c2]]
   [:div {:class "column"}
    [:p c3]]])

(defn result-component
  []
  [:div
   (row "String 1" "String 2" "Scramble")
   (for [res (:results @results)]
     (row (:str1 res) (:str2 res) (:result res)))])

(defn get-val-by-id [id]
  (-> js/document
      (.getElementById id)
      (.-value)))

(defn scramble-button
  []
  [:a {:class "button"
       :href "#"
       :on-click (fn [e]
                   (let [str1 (:str1 @results) ;(get-val-by-id "str1")
                         str2 (:str2 @results)] ;(get-val-by-id "str2")]
                     (rf/dispatch [:scramble {:str1 str1
                                              :str2 str2}])))}
   "Scramble?"])

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


(defn input [id type label]
  [:div
   [:label {:for id} label]
   [:input {:type type
            :id id
            :on-change (fn [e]
                         (rf/dispatch
                          [(keyword id)
                           (-> e
                             (.-target)
                             (.-value))]))}]])


(defn scramble-form
  []
  (fn []
    [:div
     (input "str1" "text" "First String")
     (input "str2" "text" "Second String")
     [scramble-button]]))


(rf/reg-event-db
 :str1
 (fn [_ [_ value]]
   (swap! results assoc :str1 value)))


(rf/reg-event-db
 :str2
 (fn [_ [_ value]]
   (swap! results assoc :str2 value)))

(defn app
  []
  [:div
   [:h2 "Scramble Frontend"]
   [scramble-form]
   [result-component]])



(defn ^:dev/after-load start
  []
  (r/render [app]
            (.getElementById js/document "app")))

(defn ^:export init
  []
  (start))
