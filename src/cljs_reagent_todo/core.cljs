(ns cljs-reagent-todo.core
    (:require [reagent.core :as r]
              [reagent.dom :as d]))

;; -------------------------
;; Views
(def todos (r/atom
            []))

(defn todo-item [todo]
  [:li {:style {:color (if (:completed todo) "green" "red")} :key (str todo)}(:desc todo)])

(defn todo-list []
  [:ul 
   (for [todo @todos]
     (todo-item todo))])

(defn todo-form []
  (let [novo-item (r/atom "")
        novo-item-completado (r/atom false)]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (swap! todos conj {:completed @novo-item-completado :desc @novo-item :key @novo-item})
                           (reset! novo-item "")
                           (reset! novo-item-completado false))}
       [:input {:type "checkbox" :value @novo-item-completado :checked @novo-item-completado :on-change #(reset! novo-item-completado (-> % .-target .-checked))}]
       [:input {:type "text"
                :value @novo-item
                :placeholder "Adicione um novo item"
                :on-change #(reset! novo-item (-> % .-target .-value))}]])))

(defn home-page []
  [:header [:h2 "Welcome to Reagent"]
   [:p "Adicione um novo item abaixo: "]
   [todo-form]
   [todo-list]])

;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
