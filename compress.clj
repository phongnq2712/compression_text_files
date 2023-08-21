(ns compress
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn get-frequency [frequency-map word]
  (or (frequency-map word) word))

(defn load-frequency-file []
  (let [frequency-file "frequency.txt"
        lines (line-seq (io/reader frequency-file))]
    (reduce (fn [freq-map line]
           (let [words (string/split line #"\s+")
                 indexed-words (map-indexed vector words)]
            
             (reduce (fn [acc [idx word]]
                       (if (contains? acc word)
                         acc
                            (assoc acc word idx)))
                     freq-map
                     indexed-words)
             ))
         {} lines)))

(defn combine-lists [list1 list2]
  (map #(if (number? %2) %2 (nth list1 %1)) (range) list2))

(defn compress-content [content]
  (let [frequency-map (load-frequency-file)
        words (string/split content #"\s+")
        lower-words (string/split (string/lower-case content) #"\s+")
        ;; list 1: list containing first occurrence position
        compressed-words (map #(get-frequency frequency-map %) words)
        ;; list 2: list containing lowercase words position
        compressed-lower-words (map #(get-frequency frequency-map %) lower-words)
        ;; combine list 1 to list 2
        combined-list (combine-lists compressed-words compressed-lower-words)]
    (apply str (interpose " " combined-list))    
    ))

(defn write-compressed-file [file-name content]
  (let [compressed-file (str file-name ".ct")]
    (spit compressed-file content)))

(defn process-characters-compress [string]
  (-> string
      (string/replace "," " ,")
      (string/replace "[" "[ ")
      (string/replace "(" "( ")
      (string/replace "]" " ]")
      (string/replace ")" " )")
      (string/replace "@" "@ ")
      (string/replace #"\b(\d+)\b" "@$1@")
      (string/replace "?" " ?")))

(defn process-characters-uncompress [content]
  (-> content
      (string/replace #"\@(\d+)\@" "$1")
      (string/replace " ," ",")
      (string/replace "( " "(")
      (string/replace "[ " "[")
      (string/replace " ]" "]")
      (string/replace " )" ")")
      (string/replace "@ " "@")
      (string/replace " ?" "?")
      (string/replace #"\.\s+[a-z]"
               #(str ". " (string/upper-case (subs % 2))))
      ))

(defn compress-file []
  (flush)
  (try
    (let [file-name (read)
          content (slurp (str file-name))
          preprocessed-content (process-characters-compress content)
          compressed-content (compress-content preprocessed-content)]
      (write-compressed-file file-name compressed-content)
      (println "File compressed successfully."))
  (catch Exception e
      (println "Oops: specified file does not exist."))))
  
(defn get-words [frequency-map numbers]
  (->> numbers
       (map (fn [n]
              (let [word (reduce-kv (fn [acc w num]
                                      (if (= n (int num))
                                        (subs (name w) 0)
                                        acc))
                                    nil
                                    frequency-map)]
                (if word
                  word
                  (str n)))))
       ))

(defn convert-to-int [x]
  (if (and (string? x) (re-matches #"^-?\d+$" x))
    (Integer/parseInt x)
    x))

(defn uncompress-content [content]
  (let [frequency-map (load-frequency-file)
        numbers (map convert-to-int (string/split content #"\s+"))
        words (get-words frequency-map numbers)
        ]
        (apply str (interpose " " words))))

(defn uncompress-file []
  (flush)
  (try
    (let [file-name (read)
          compressed-content (slurp (str file-name))
          decompressed-content (uncompress-content compressed-content)
          result (process-characters-uncompress decompressed-content)]
      (println result))
  (catch Exception e
    (println "Oops: specified file does not exist." (.getMessage e) ))))