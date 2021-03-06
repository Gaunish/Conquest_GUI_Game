#!/bin/bash

./gradlew build || exit 1
./gradlew cloverAggregateReport || exit 1
scripts/coverage_summary.sh
cp -r server/build/reports/clover/html/* /coverage-out/ || exit 1
cp -r client/build/reports/clover/html/* /coverage-out/ || exit 1
cp -r common/build/reports/clover/html/* /coverage-out/ || exit 1
