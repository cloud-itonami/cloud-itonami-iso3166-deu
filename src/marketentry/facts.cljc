(ns marketentry.facts
  "Germany (DEU) public-procurement market-entry regulatory catalog.

  Every field below traces to a verified primary/official source, checked
  on :verified-at:
    - Business registration: Handelsregister, maintained locally by the
      Amtsgerichte (district courts) per federal state, exposed nationally
      via the free portal handelsregister.de (free since 2022). Abteilung A
      covers partnerships/sole traders; Abteilung B covers capital
      companies (HRB/HRA numbers).
    - Public-procurement legal framework: GWB (Gesetz gegen
      Wettbewerbsbeschränkungen, transposes EU directives at statute
      level), VgV (Vergabeverordnung, above-EU-threshold procedures),
      UVgO (below-threshold procedures), VOB/A (construction).
    - E-procurement: \"e-Vergabe\" (evergabe-online.de), operated by the
      Beschaffungsamt des BMI (BeschA) under the Federal Ministry of the
      Interior (BMI) -- confirmed current, no reorganization (BeschA
      marked its 75th anniversary May 18, 2026, still under BMI).
    - Tax registration: new businesses register with the local Finanzamt
      via the \"Fragebogen zur steuerlichen Erfassung\" (filed
      electronically via ELSTER), which issues the Steuernummer. The
      USt-IdNr (VAT ID) is issued separately and exclusively by the
      Bundeszentralamt für Steuern (BZSt).

  A jurisdiction not in `catalog` has NO spec-basis, full stop -- extend
  `catalog` with a genuinely verified entry, never invent an id/url. This
  catalog previously carried unlabeled USA/JPN/GBR entries left over from
  scaffold-copy (byte-identical to boilerplate entries in unrelated
  cloud-itonami-iso3166-{bra,nga,ago} sibling repos); no README/ADR in
  this repo ever documented a deliberate comparative-jurisdiction design,
  so they were removed rather than kept as undocumented, unverified,
  un-dated foreign entries in a repo scoped to Germany.")

(def catalog
  {"DEU"
   {:name "Germany"
    :owner-authority "Beschaffungsamt des BMI (BeschA) / e-Vergabe platforms"
    :legal-basis "GWB / VgV / UVgO"
    :national-spec "e-Vergabe supplier registration under EU procurement directives"
    :provenance "https://www.evergabe-online.de/"
    :verified-at "2026-07-22"
    :e-procurement-operator "Beschaffungsamt des BMI (BeschA), under the Federal Ministry of the Interior (BMI)"
    :business-registration-authority "Handelsregister, maintained locally by the Amtsgerichte (district courts) per federal state"
    :business-registration-detail "National lookup via handelsregister.de (free since 2022); Abteilung A = partnerships/sole traders, Abteilung B = capital companies (HRB/HRA numbers)"
    :business-registration-provenance "https://www.handelsregister.de/"
    :tax-authority "Finanzamt (local), via the electronic ELSTER \"Fragebogen zur steuerlichen Erfassung\" -- issues the Steuernummer"
    :tax-authority-provenance "https://www.elster.de/"
    :required-evidence ["Handelsregister extract"
                         "e-Vergabe registration record"
                         "USt-IdNr record"
                         "Authorized-representative record"]
    :rep-owner-authority "Beschaffungsamt / contracting authorities"
    :rep-legal-basis "authorized representative / domicile requirements for non-EU bidders on certain procedures"
    :rep-provenance "https://www.evergabe-online.de/"
    :corporate-number-owner-authority "Bundeszentralamt für Steuern"
    :corporate-number-legal-basis "Umsatzsteuer-Identifikationsnummer (USt-IdNr) -- issued separately and exclusively by BZSt, distinct from the Finanzamt-issued Steuernummer"
    :corporate-number-provenance "https://www.bzst.de/"}})

(defn spec-basis [iso3] (get catalog iso3))
(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s) missing (remove catalog iso3s)]
     {:requested (count iso3s) :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-deu R0: " (count catalog) " jurisdiction(s) seeded (Germany only -- see namespace docstring for the removed-contamination note).")})))
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
