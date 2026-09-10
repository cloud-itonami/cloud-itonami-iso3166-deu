(ns statute.facts
  "General-law compliance catalog for Germany (DEU) -- extends this
  repo's existing `marketentry.facts` (public-procurement market-entry
  only, narrow scope) with a second, orthogonal catalog of statutes a
  company generally must track for compliance. Mirrors
  cloud-itonami-iso3166-jpn/-usa/-gbr's `statute.facts` (ADR-2607141700,
  cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL gesetze-im-internet.de (Bundesministerium
  der Justiz) URL -- never fabricated. A law not in this table has NO
  spec-basis, full stop; extend `catalog`, do not invent an id/url.
  Title and date were independently WebFetch-verified against the live
  gesetze-im-internet.de page on 2026-07-14 (rendered cleanly, like the
  UK's legislation.gov.uk).")

(def catalog
  "iso3 -> vector of statute entries."
  {"DEU"
   [{:statute/id "deu.aktiengesetz"
     :statute/title "Aktiengesetz (AktG, Stock Corporation Act)"
     :statute/jurisdiction "DEU"
     :statute/kind :law
     :statute/law-number "AktG"
     :statute/url "https://www.gesetze-im-internet.de/aktg/index.html"
     :statute/url-provenance :official-gesetze-im-internet
     :statute/enacted-date "1965-09-06"
     :statute/retrieved-at "2026-07-14"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "deu.bundesdatenschutzgesetz"
     :statute/title "Bundesdatenschutzgesetz (BDSG, Federal Data Protection Act)"
     :statute/jurisdiction "DEU"
     :statute/kind :law
     :statute/law-number "BDSG"
     :statute/url "https://www.gesetze-im-internet.de/bdsg_2018/BJNR209710017.html"
     :statute/url-provenance :official-gesetze-im-internet
     :statute/enacted-date "2017-06-30"
     :statute/retrieved-at "2026-07-14"
     :statute/topic #{:data-protection :privacy}}
    {:statute/id "deu.kuendigungsschutzgesetz"
     :statute/title "Kündigungsschutzgesetz (KSchG, Protection Against Dismissal Act)"
     :statute/jurisdiction "DEU"
     :statute/kind :law
     :statute/law-number "KSchG"
     :statute/url "https://www.gesetze-im-internet.de/kschg/BJNR004990951.html"
     :statute/url-provenance :official-gesetze-im-internet
     :statute/enacted-date "1951-08-10"
     :statute/retrieved-at "2026-07-14"
     :statute/topic #{:labor :employment}}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-deu statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "DEU")) " DEU statutes seeded with an "
                 "official gesetze-im-internet.de citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
