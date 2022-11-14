(ns cljs-reagent-todo.core
    (:require [reagent.dom :as d]))

;; -------------------------
;; Views
(defn todo-item [todo]
  [:li {:style {:color (:color todo)}}(:desc todo)])

(defn home-page []
  [:header [:h2 "Welcome to Reagent"]
   [:p "Adicione um novo item abaixo: "]
   [:ul
    [todo-item {:desc "Cozinhar a massa" :color "green"}]]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
