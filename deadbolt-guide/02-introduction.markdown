# Introduction #
At the date of writing this, the small code experiment that turned into Deadbolt is just over two years old.  In that time, it has grown in capabilities, power and - hopefully - usefulness.  It has certainly reached the point where a few badly-written lines of text can adequately cover its features and use.  Once I factored in that it now has support for Java and Scala, and that its new architecture allows for easy extension with other languages, it became clear that a short booklet (or booklet-like document) was required.

I hope this document (booklet-like or otherwise) will prove useful as you integrate Deadbolt 2 (also known as D2, because I'm tired of constantly writing Deadbolt 2) into your application.

## History ##
Back in September 2010, I was embarking on my first project using the Play! Framework (version 1.03.2 for fans of unnecessary detail) and discovering the Secure module it shipped with was unsuitable for the required authorisation.  As a result, Deadbolt 1.0 was written to provide AND/OR/NOT support for roles.  Sometime later, dynamic rule support was added and other new features would be released as use cases and bug reports cropped up.

Deadbolt 2 was released at the same time as Play 2.0, and was essentially the logic of Deadbolt 1 exposed in the Play 2 style.  Nine months after that initial release - nine months, I should add, of woefully inadequate Scala support - I re-designed the architecture to a more modular approach.  There is now a core module written in Java, and separate idiomatic modules for Java and Scala.  This is slightly different to the architecture of Play 2 itself, where the core and the Scala API are co-located.

## Authorisation? Shouldn't that be authorization? ##
I'm British.  Deal with it.

## Acknowledgements ##
This space for hire!  Review this text, and get your name here :)

## Thanks ##
Don't worry, the touchy-feely stuff is nearly out of the way.

I'd like to thank Guilluame Bort for introducing Play! in the first place, and all the people who have developed, patched and improved it ever since.

I would also like to thank everyone that tried Deadbolt, asked for features, reported bugs and generally drove me to improve it.  Your constant, irritating requests and badgering questions have made Deadbolt what it is today =)

Finally, and always, to Greet and Lotte.

End of touchy feely stuff.