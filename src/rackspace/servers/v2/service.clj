(ns rackspace.servers.v2.service
  (:require [clojure.data.json :as json]
            [rackspace.const :as const]
            [rackspace.services :as services]
            [clj-http.client :as http]
            [rackspace.util :as util]
            [rackspace.identity :as identity]))

(defn get-new-server-payload [server-name image-id flavor-id]
  {:body (json/write-str {:server
                          {:name server-name
                           :imageRef image-id
                           :flavorRef flavor-id}})
   :headers {"Content-Type" "application/json"}})


(defn get-flavors-list 
  [identity-response region]
  (-get-list-by-type 
    identity-response  "flavors" (keyword region)))

(defn get-images-list 
  [identity-response region]
  (-get-list-by-type 
    identity-response  "images" (keyword region)))

(defn -get-data 
  [identity-response url-path region]
  (let [base-url (services/get-cloud-servers-region-url 
                   identity-response
                   region)
        url (str base-url url-path)
        ]
    
    (util/parse-json-body 
      (http/get 
        url
        {:headers
         {const/x-auth-token (identity/get-token identity-response)}}))))

(defn -get-list-by-type
  [identity-response request-type region]
  (map
    #(hash-map (:name %1) (:id %1))
    ((keyword request-type) (-get-data 
                              identity-response 
                              (str "/" request-type) 
                              region))))(ns rackspace.servers.v2.service
  (:require [clojure.data.json :as json]
            [rackspace.const :as const]
            [rackspace.services :as services]
            [clj-http.client :as http]
            [rackspace.util :as util]
            [rackspace.identity :as identity]))

(defn get-new-server-payload [server-name image-id flavor-id]
  {:body (json/write-str {:server
                          {:name server-name
                           :imageRef image-id
                           :flavorRef flavor-id}})
   :headers {"Content-Type" "application/json"}})


(defn get-flavors-list 
  [identity-response region]
  (-get-list-by-type 
    identity-response  "flavors" (keyword region)))

(defn get-images-list 
  [identity-response region]
  (-get-list-by-type 
    identity-response  "images" (keyword region)))

(defn -get-data 
  [identity-response url-path region]
  (let [base-url (services/get-cloud-servers-region-url 
                   identity-response
                   region)
        url (str base-url url-path)
        ]
    
    (util/parse-json-body 
      (http/get 
        url
        {:headers
         {const/x-auth-token (identity/get-token identity-response)}}))))

(defn -get-list-by-type
  [identity-response request-type region]
  (map
    #(hash-map (:name %1) (:id %1))
    ((keyword request-type) (-get-data 
                              identity-response 
                              (str "/" request-type) 
                              region))))