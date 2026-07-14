(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest deu-has-spec-basis
  (let [sb (facts/spec-basis "DEU")]
    (is (= 3 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://www.gesetze-im-internet.de/") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["DEU" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["deu.kuendigungsschutzgesetz"]
         (mapv :statute/id (facts/by-topic "DEU" :labor))))
  (is (empty? (facts/by-topic "DEU" :environment)))
  (is (empty? (facts/by-topic "ATL" :labor))))
