# Transit handlers for DataScript

This library enables reading and writing [DataScript](https://github.com/tonsky/datascript) database to/from [Transit](https://github.com/cognitect/transit-format) messages for both Clojure and ClojureScript.


## Usage

Add this to your `project.clj`:

```clj
[datascript "0.18.1"]
[datascript-transit "0.3.0"]
```

If you want to use another transit version:

```clj
[datascript "0.18.1"]
[datascript-transit "0.3.0"
  :exclusions [com.cognitect/transit-clj
               com.cognitect/transit-cljs]
[com.cognitect/transit-clj  "0.8.288"]
[com.cognitect/transit-cljs "0.8.239"]
```

If the only custom transit handler you need is DataScript, just use functions from `datascript.transit` namespace which reads/writes to a string directly:

```clj
(require '[datascript.transit :as dt])

(-> (datascript.core/empty-db)
    (dt/write-transit-str) ;; => string
    (dt/read-transit-str)) ;; => DataScript DB
```

If you use your own serialization/deserialization, add these handlers to the mix:

```clj
datascript.transit/read-handlers
datascript.transit/write-handlers
```


## Changes

### 0.3.0

Updated to `[datascript 0.18.0]` (PR#5, thx @dimovich)

### 0.2.2

Preserve `added` flag on Datoms (you can now correctly serialize transaction data)

### 0.2.1

Bumped deps:

```clj
  [org.clojure/clojure        "1.8.0"  :scope "provided"]
  [org.clojure/clojurescript  "1.9.89" :scope "provided"]
  [datascript                 "0.15.1" :scope "provided"]
  [com.cognitect/transit-clj  "0.8.285"]
  [com.cognitect/transit-cljs "0.8.239"]
```
    
### 0.2.0

Updated to `[datascript 0.13.0]`

### 0.1.0

Initial release.


## License

Copyright © 2015 Nikita Prokopov

Licensed under Eclipse Public License (see [LICENSE](LICENSE)).
