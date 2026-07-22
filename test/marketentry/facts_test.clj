(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest deu-has-spec-basis
  (let [sb (facts/spec-basis "DEU")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/rep-spec-basis "DEU")))
    (is (some? (facts/corporate-number-spec-basis "DEU")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "DEU")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "DEU" all)))
    (is (not (facts/required-evidence-satisfied? "DEU" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["DEU" "ATL" "ZZZ"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["DEU"] (:covered-jurisdictions c)))
    (is (= ["ATL" "ZZZ"] (:missing-jurisdictions c)))))

(deftest catalog-is-germany-only
  (testing "no unlabeled foreign-jurisdiction contamination (scaffold-copy incident)"
    (is (= #{"DEU"} (set (keys facts/catalog))))))

(deftest deu-entry-cites-verified-authorities
  (let [sb (facts/spec-basis "DEU")]
    (is (= "GWB / VgV / UVgO" (:legal-basis sb)))
    (is (re-find #"Beschaffungsamt des BMI" (:owner-authority sb)))
    (is (re-find #"Bundesministerium|BMI" (:e-procurement-operator sb)))
    (is (re-find #"Amtsgerichte" (:business-registration-authority sb)))
    (is (re-find #"Finanzamt" (:tax-authority sb)))
    (is (= "Bundeszentralamt für Steuern" (:corporate-number-owner-authority sb)))))
