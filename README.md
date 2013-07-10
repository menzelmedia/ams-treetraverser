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

