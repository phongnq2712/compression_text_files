(ns menu
  (:require [compress]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(defn display-list-of-files []
  (let [files (file-seq (io/file "."))]
    (doseq [file files]
      (println (str "* ./" (.getName file))))))

(defn display-file-contents []
  (flush)
  (try
    (let [file-name (read)
        content (slurp (str file-name))]
      (println "")
      (print content)
      (println ""))
  (catch Exception e
    (println "Oops: specified file does not exist.")
    ))
  )

(defn menu []
  (println "----------------------------------")
  (println "*** Compression Menu ***")
  (println "1. Display list of files")
  (println "2. Display file contents")
  (println "3. Compress a file")
  (println "4. Uncompress a file")
  (println "5. Exit")
  (println "")
  (print "Enter an option? ")
  (flush)
  (let [choice (read)]
    (cond
      (= choice 1) (do (println "File List:") (display-list-of-files) (recur))
      (= choice 2) (do (print "Please enter a file name: ") (display-file-contents) (recur))
      (= choice 3) (do (print "Please enter a file name: ") (compress/compress-file) (recur))
      (= choice 4) (do (print "Please enter a file name: ") (compress/uncompress-file) (recur))
      (= choice 5) (println "Exiting menu...")
      :else (do (println "Invalid choice!") (recur)))))

(menu)