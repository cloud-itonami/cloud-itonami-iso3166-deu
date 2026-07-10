(ns marketentry.facts
  "Germany market-entry regulatory catalog (G2-style).")

(def catalog
  {"DEU" {:name "Germany"
          :owner-authority "Beschaffungsamt des BMI / e-Vergabe platforms"
          :legal-basis "GWB / VgV / UVgO"
          :national-spec "e-Vergabe supplier registration under EU procurement directives"
          :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract"
                              "e-Vergabe registration record"
                              "USt-IdNr record"
                              "Authorized-representative record"]
          :rep-owner-authority "Beschaffungsamt / contracting authorities"
          :rep-legal-basis "authorized representative / domicile requirements for non-EU bidders on certain procedures"
          :rep-provenance "https://www.evergabe-online.de/"
          :corporate-number-owner-authority "Bundeszentralamt für Steuern"
          :corporate-number-legal-basis "Umsatzsteuer-Identifikationsnummer (USt-IdNr)"
          :corporate-number-provenance "https://www.bzst.de/"}
   "USA" {:name "United States" :owner-authority "GSA/SAM.gov" :legal-basis "FAR"
          :national-spec "SAM.gov" :provenance "https://sam.gov/"
          :required-evidence ["EIN record" "SAM.gov registration record"
                              "State business registration record" "SAM UEI verification record"]}
   "JPN" {:name "Japan" :owner-authority "GEPS" :legal-basis "unified qualification"
          :national-spec "GEPS" :provenance "https://www.chotatujoho.go.jp/va/com/ShikakuTop.html"
          :required-evidence ["法人番号確認記録" "全省庁統一資格申請記録"
                              "GEPS 事業者登録記録" "日本居住代理人確認記録"]}
   "GBR" {:name "United Kingdom" :owner-authority "Find a Tender"
          :legal-basis "Public Contracts Regulations 2015"
          :national-spec "Find a Tender" :provenance "https://www.find-tender.service.gov.uk/"
          :required-evidence ["Companies House record" "Find a Tender registration record"
                              "VAT registration record" "Authorized-representative record"]}})

(defn spec-basis [iso3] (get catalog iso3))
(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s) missing (remove catalog iso3s)]
     {:requested (count iso3s) :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-deu R0: " (count catalog) " jurisdictions seeded.")})))
(defn required-evidence-satisfied? [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (= (count required-evidence) (count (filter (set submitted) required-evidence)))))
(defn evidence-checklist [iso3] (:required-evidence (spec-basis iso3) []))
(defn rep-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))
(defn corporate-number-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority :corporate-number-legal-basis :corporate-number-provenance]))))
