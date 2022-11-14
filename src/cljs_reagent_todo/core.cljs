(ns cljs-reagent-todo.core
    (:require [reagent.core :as r]
              [reagent.dom :as d]))

;; -------------------------
;; Views
(def todos (r/atom
            []))

(defn todo-item [todo]
  [:li {:style {:color (if (:completed todo) "green" "red")} :key (str todo)}(:desc todo)])

(defn todo-form []
  (let [novo-item (r/atom "")]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (swap! todos conj {:completed false :desc @novo-item :key @novo-item})
                           (reset! novo-item ""))}
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
