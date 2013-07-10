AMS TreeTraverser
=================

SYNOPSIS
--------
AMS TreeTraverser is a simple reflection based bean traverser, which makes it extremely simple to map large object trees.


As Simple as 1, 2, 3
-----------
1. Tag a class with the TreeIteratorHandler tagging interface and implement enter() or leave() callback methods for every class in the object tree you are interested in.
2. Fire up the Traverser on the root node (or every other starting node): 
	<pre>
	ReflectionTreeTraverser traverser = new ReflectionTreeTraverser();
        traverser.setHandler(handler);
        traverser.treeTraverse(cat);
	</pre>
3. You can restrict the traversing to packages with setRestrictToPackage[s]()   ( for mapping hibernate proxies without the shit etcpp)

Copyright (C) 2012 by Mondia Media GmbH All rights reserved.

Redistribution and use in source and binary forms are permitted provided that the above copyright notice and this paragraph are duplicated in all such forms and that any documentation, advertising materials, and other materials related to such distribution and use acknowledge that the software was developed by the Mondia Media GmbH. The name of the Mondia Media GmbH may not be used to endorse or promote products derived from this software without specific prior written permission. THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
