;;   Copyright (c) Zachary Tellman. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;;   which can be found in the file epl-v10.html at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns
  ^{:skip-wiki true}
  aleph.http
  (:use
    [aleph core]
    [potemkin])
  (:require
    [aleph.http.server :as server]
    [aleph.http.client :as client]
    [aleph.http.utils :as utils]
    [aleph.http.policy-file :as policy]))

(import-fn server/start-http-server)

(import-fn client/raw-http-client)
(import-fn client/http-request)
(import-fn #'client/close-http-client)

(defn sync-http-request
  ([request]
     (sync-http-request (raw-http-client request) request))
  ([client request]
     (->> (http-request client request)
       wait-for-pipeline)))

(import-fn policy/start-policy-file-server)

(defn wrap-ring-handler [f]
  (fn [channel request]
    (enqueue-and-close channel
      (f request))))


