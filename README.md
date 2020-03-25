# text-matcher

An application that looks for set of predefined names in a file.
Names are looked by pattern wich involves an entry in the text ignoring case (upper/lower).

Entry point class: com.bigid.EntryPoint
Dividing on threads logic: com.bigid.dsl.MultiThreadSearchEngine#getBlockSearchResults
Matching: com.bigid.matcher.impl.NamesKeyWordsMatcher#match
Aggregation: com.bigid.aggregator.impl.NamesMapsAggregator#aggregate

Several tests were provided with primitive assertion: com.bigid.service.TextSearchingServiceTest
com.bigid.service.TextSearchingServiceTest
