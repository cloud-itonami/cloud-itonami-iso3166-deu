(ns culture.facts
  "Country-level regional-culture catalog for Germany (DEU) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"DEU"
   [{:culture/id "deu.dish.sauerbraten"
     :culture/name "Sauerbraten"
     :culture/country "DEU"
     :culture/kind :dish
     :culture/summary "Heavily marinated roast meat dish, regarded as a national dish of Germany and frequently served in German-style restaurants internationally."
     :culture/url "https://en.wikipedia.org/wiki/Sauerbraten"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "deu.dish.bratwurst"
     :culture/name "Bratwurst"
     :culture/country "DEU"
     :culture/kind :dish
     :culture/summary "Type of German sausage; the first documented evidence in Germany dates to 1313 in the Franconian city of Nuremberg."
     :culture/url "https://en.wikipedia.org/wiki/Bratwurst"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "deu.dish.black-forest-gateau"
     :culture/name "Black Forest gateau"
     :culture/country "DEU"
     :culture/kind :dish
     :culture/summary "Layer cake of cocoa, cherries, Kirsch and whipped cream; its origins are disputed, and a 1927 recipe is kept at an archive in Radolfzell, Germany."
     :culture/url "https://en.wikipedia.org/wiki/Black_Forest_gateau"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "deu.dish.currywurst"
     :culture/name "Currywurst"
     :culture/country "DEU"
     :culture/kind :dish
     :culture/summary "German fast-food dish of pork sausage topped with curry ketchup, invented in 1949 by Herta Heuwer at a food stand in West Berlin."
     :culture/url "https://en.wikipedia.org/wiki/Currywurst"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "deu.beverage.german-beer"
     :culture/name "German beer"
     :culture/country "DEU"
     :culture/kind :beverage
     :culture/summary "Beer is a major part of German culture; under the Reinheitsgebot tradition only water, hops, yeast and malt are permitted as ingredients in its production."
     :culture/url "https://en.wikipedia.org/wiki/Beer_in_Germany"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "deu.craft.cuckoo-clock"
     :culture/name "Cuckoo clock"
     :culture/country "DEU"
     :culture/kind :craft
     :culture/summary "Clock whose development and evolution centred on the Black Forest area of southwestern Germany, from where it was exported worldwide from the mid-1850s; it has become a cultural icon of Germany."
     :culture/url "https://en.wikipedia.org/wiki/Cuckoo_clock"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "deu.craft.meissen-porcelain"
     :culture/name "Meissen porcelain"
     :culture/country "DEU"
     :culture/kind :craft
     :culture/summary "The first European hard-paste porcelain, produced at the royal factory in Meissen, Saxony from 1710; one of the oldest and most internationally known German luxury brands."
     :culture/url "https://en.wikipedia.org/wiki/Meissen_porcelain"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "deu.festival.oktoberfest"
     :culture/name "Oktoberfest"
     :culture/country "DEU"
     :culture/kind :festival
     :culture/summary "The world's largest Volksfest, combining a beer festival and a fun fair, held annually in Munich since 1810 and drawing around seven million visitors each year."
     :culture/url "https://en.wikipedia.org/wiki/Oktoberfest"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "deu.heritage.cologne-cathedral"
     :culture/name "Cologne Cathedral"
     :culture/country "DEU"
     :culture/kind :heritage
     :culture/summary "Renowned monument of German Catholicism and Gothic architecture in Cologne, declared a UNESCO World Heritage Site in 1996; Germany's most visited landmark."
     :culture/url "https://en.wikipedia.org/wiki/Cologne_Cathedral"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

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
      :note (str "cloud-itonami-iso3166-deu culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "DEU"))
                 " DEU entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
