(ns cljs-reagent-todo.core
    (:require [reagent.core :as r]
              [reagent.dom :as d]))

;; -------------------------
;; Views
(def todos (r/atom
            [{:desc "Cozinhar a massa" :color "green"}]))

(defn todo-item [todo]
  [:li {:style {:color (:color todo)}}(:desc todo)])

(defn todo-form []
  (let [novo-item (r/atom "")]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (swap! todos conj {:color "green" :desc @novo-item}))}
       [:input {:type "text"
                :value @novo-item
                :placeholder "Adicione um novo item"
                :on-change #(reset! novo-item (-> % .-target .-value))}]])))

(defn home-page []
  [:header [:h2 "Welcome to Reagent"]
   [:p "Adicione um novo item abaixo: "]
   [todo-form]
   [:ul
    (for [todo @todos]
      (todo-item todo))]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
