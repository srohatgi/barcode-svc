# Barcode Service #

Service built for parsing driver license information from the aamva barcode from back of a typical driver license.

## Running ##

    $ ./gradlew bRun

## Testing ##

    $ curl -F file=@./dl-back.jpg localhost:8080                 # upload a driver license
    $ curl -XPOST http://localhost:8080/parse/files/dl-back.jpg  # parse information
