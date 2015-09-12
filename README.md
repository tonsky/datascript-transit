# Transit handlers for DataScript

This library enables reading and writing [DataScript](https://github.com/tonsky/datascript) database to/from [Transit](https://github.com/cognitect/transit-format) messages for both Clojure and ClojureScript.


## Usage

Add this to your `project.clj`:

```clj
[datascript "0.12.1"]
[datascript-transit "0.1.0"]
```

If you want to use another transit version:

```clj
[datascript "0.12.1"]
[datascript-transit "0.1.0"
  :exclusions [com.cognitect/transit-clj
               com.cognitect/transit-cljs]
[com.cognitect/transit-clj  "0.8.281"]
[com.cognitect/transit-cljs "0.8.225"]
```

If the only custom transit handler you need is DataScript, just use functions from `datascript.transit` namespace which reads/writes to a string directly:

```clj
(require '[datascript.transit :as dt])

(-> (datascript/empty-db)
    (dt/write-transit-str) ;; => string
    (dt/read-transit-str)) ;; => DataScript DB
```

If you use your own serialization/deserialization, add these handlers to the mix:

```clj
datascript.transit/read-handlers
datascript.transit/write-handlers
```


## Changes

### 0.1.0

Initial release.


## License

Copyright © 2015 Nikita Prokopov

Licensed under Eclipse Public License (see [LICENSE](LICENSE)).
