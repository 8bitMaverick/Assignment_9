/* Courtney Hunt JAVA 1B Assignment 9 Foothill College
 * 
 * This program creates a tree, adds leaves to it, clones the tree, removes a
 * few leaves via soft deletion by marking the boolean deleted as true, from
 * that point on, the virtual lists will not display the marked leaves nor their
 * children, then garbage is collected meaning all the leaves flagged as deleted
 * and their children are removed via hard deletion. A virtual display and a
 * physical display with virtual and physical size counts for the tree is shown
 * after each step for clarity.
 */
//------------------------------------------------------
public class Foothill
{ 
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      FHsdTree<String> sceneTree = new FHsdTree<String>();
      FHsdTreeNode<String> tn;

      // create a scene in a room
      tn = sceneTree.addChild(null, "room");

      // add three objects to the scene tree
      sceneTree.addChild(tn, "Lily the canine");
      sceneTree.addChild(tn, "Miguel the human");
      sceneTree.addChild(tn, "table");
      // add some parts to Miguel
      tn = sceneTree.find("Miguel the human");

      // Miguel's left arm
      tn = sceneTree.addChild(tn, "torso");
      tn = sceneTree.addChild(tn, "left arm");
      tn =  sceneTree.addChild(tn, "left hand");
      sceneTree.addChild(tn, "thumb");
      sceneTree.addChild(tn, "index finger");
      sceneTree.addChild(tn, "middle finger");
      sceneTree.addChild(tn, "ring finger");
      sceneTree.addChild(tn, "pinky");

      // Miguel's right arm
      tn = sceneTree.find("Miguel the human");
      tn = sceneTree.find(tn, "torso", 0);
      tn = sceneTree.addChild(tn, "right arm");
      tn =  sceneTree.addChild(tn, "right hand");
      sceneTree.addChild(tn, "thumb");
      sceneTree.addChild(tn, "index finger");
      sceneTree.addChild(tn, "middle finger");
      sceneTree.addChild(tn, "ring finger");
      sceneTree.addChild(tn, "pinky");

      // add some parts to Lily
      tn = sceneTree.find("Lily the canine");
      tn = sceneTree.addChild(tn, "torso");
      sceneTree.addChild(tn, "right front paw");
      sceneTree.addChild(tn, "left front paw");
      sceneTree.addChild(tn, "right rear paw");
      sceneTree.addChild(tn, "left rear paw");
      sceneTree.addChild(tn, "spare mutant paw");
      sceneTree.addChild(tn, "wagging tail");

      // add some parts to table
      tn = sceneTree.find("table");
      sceneTree.addChild(tn, "north east leg");
      sceneTree.addChild(tn, "north west leg");
      sceneTree.addChild(tn, "south east leg");
      sceneTree.addChild(tn, "south west leg");

      // first display after adding to tree
      createLine(80, "-");
      System.out.println("Virtual List After Initial Additions:");
      createLine(80, "-");
      sceneTree.display();
      System.out.println("\nVirtual Size: " + sceneTree.size());
      System.out.println("Physical Size: " + sceneTree.sizePhysical() + "\n");

      createLine(80, "-");
      System.out.println("Physical List After Initial Additions:");
      createLine(80, "-");
      sceneTree.displayPhysical();
      System.out.println("\nVirtual Size: " + sceneTree.size());
      System.out.println("Physical Size: " + sceneTree.sizePhysical() + "\n");

      // clone
      FHsdTree<String> ClonedTree = (FHsdTree<String>) sceneTree.clone();

      // remove some nodes
      sceneTree.remove("spare mutant paw");
      sceneTree.remove("Miguel the human");
      sceneTree.remove("an imagined higgs boson");

      // display after removal of some nodes
      createLine(80, "-");
      System.out.println("Virtual List After Removal:");
      createLine(80, "-");
      sceneTree.display();
      System.out.println("\nVirtual Size: " + sceneTree.size());
      System.out.println("Physical Size: " + sceneTree.sizePhysical() + "\n");

      createLine(80, "-");
      System.out.println("Physical List After Removal:");
      createLine(80, "-");
      sceneTree.displayPhysical();
      System.out.println("\nVirtual Size: " + sceneTree.size());
      System.out.println("Physical Size: " + sceneTree.sizePhysical() + "\n");

      sceneTree.collectGarbage();

      // display garbage collection
      createLine(80, "-");
      System.out.println("Virtual List After Garbage Colletction:");
      createLine(80, "-");
      sceneTree.display();
      System.out.println("\nVirtual Size: " + sceneTree.size());
      System.out.println("Physical Size: " + sceneTree.sizePhysical() + "\n");

      createLine(80, "-");
      System.out.println("Physical List After Garbage Colletction:");
      createLine(80, "-");
      sceneTree.displayPhysical();
      System.out.println("\nVirtual Size: " + sceneTree.size());
      System.out.println("Physical Size: " + sceneTree.sizePhysical() + "\n");

      // see if the clone workedcreateLine(80, "-");
      createLine(80, "-");
      System.out.println("Clone display");
      createLine(80, "-");
      ClonedTree.display();
      System.out.println("\nClone's Virtual Size: " + ClonedTree.size());
      System.out.println("Clone's Physical Size: " + ClonedTree.sizePhysical() + "\n");

   }

   // helps beautify the console output
   private static void createLine(int length, String shape)
   {
      for (int counter = 0; counter < length; counter += 1)
      {
         System.out.print(shape);
      }
   }

}

class FHsdTree<E> implements Cloneable
{
   private int mSize;
   FHsdTreeNode<E> mRoot;

   public FHsdTree() { clear(); }
   public boolean empty() { return (mSize == 0); }
   public int size()  { return size(mRoot, 0); }
   public int sizePhysical() { return mSize; }
   public void clear() { mSize = 0; mRoot = null; }

   public FHsdTreeNode<E> find(E x) { return find(mRoot, x, 0); }
   public boolean remove(E x) { return remove(mRoot, x); }
   public void  display()  { display(mRoot, 0); }   
   public void  displayPhysical()  { displayPhysical(mRoot, 0); }

   public < F extends Traverser< ? super E > > 
   void traverse(F func)  { traverse(func, mRoot, 0); }

   public FHsdTreeNode<E> addChild( FHsdTreeNode<E> treeNode,  E x )
   {
      // empty tree? - create a root node if user passes in null
      if (mSize == 0)
      {
         if (treeNode != null)
            return null; // error something's fishy.  treeNode can't right
         mRoot = new FHsdTreeNode<E>(x, null, null, null, false);
         mRoot.myRoot = mRoot;
         mSize = 1;
         return mRoot;
      }
      if (treeNode == null)
         return null; // error inserting into non_null tree with a null parent
      if (treeNode.myRoot != mRoot)
         return null;  // silent error, node does not belong to this tree

      // push this node into the head of the sibling list; adjust prev pointers
      FHsdTreeNode<E> newNode = new FHsdTreeNode<E>(x, treeNode.firstChild,
            null, treeNode, mRoot, false);  // sb, chld, prv, rt, del
      treeNode.firstChild = newNode;
      if (newNode.sib != null)
         newNode.sib.prev = newNode;
      ++mSize;
      return newNode;  
   }

   public FHsdTreeNode<E> find(FHsdTreeNode<E> root, E x, int level)
   {
      FHsdTreeNode<E> retval;

      if (mSize == 0 || root == null)
         return null;

      // if we found a perfect match and boolean deleted is not true, return it
      if (root.getData().equals(x) && root.deleted != true)
         return root;

      // otherwise, recurse.  don't process sibs if this was the original call
      if ( level > 0 && (retval = find(root.sib, x, level)) != null )
         return retval;
      return find(root.firstChild, x, ++level);
   }

   public boolean remove(FHsdTreeNode<E> root, E x)
   {
      FHsdTreeNode<E> tn = null;

      if (mSize == 0 || root == null)
         return false;

      if ( (tn = find(root, x, 0)) != null)
      {
         tn.deleted = true;
         return true;
      }
      return false;
   }

   private void removeNode(FHsdTreeNode<E> nodeToDelete )
   {
      if (nodeToDelete == null || mRoot == null)
         return;
      if (nodeToDelete.myRoot != mRoot)
         return;  // silent error, node does not belong to this tree

      // remove all the children of this node
      while (nodeToDelete.firstChild != null)
      {
         removeNode(nodeToDelete.firstChild);
         mSize--;
      }

      if (nodeToDelete.prev == null)
         mRoot = null;  // last node in tree
      else if (nodeToDelete.prev.sib == nodeToDelete)
         nodeToDelete.prev.sib = nodeToDelete.sib; // adjust left sibling
      else
         nodeToDelete.prev.firstChild = nodeToDelete.sib;  // adjust parent

      // adjust the successor sib's prev pointer
      if (nodeToDelete.sib != null)
         nodeToDelete.sib.prev = nodeToDelete.prev;
   }

   public <F extends Traverser<? super E>> boolean collectGarbage()
   {
      hardDelete(this.mRoot);

      return false;
   }

   public <F extends Traverser<? super E>> boolean collectGarbage(
         FHsdTreeNode<E> treeNode)
   {
      hardDelete(treeNode);

      return false;
   }

   protected <F extends Traverser<? super E>> void hardDelete(
         FHsdTreeNode<E> treeNode)
   {
      FHsdTreeNode<E> child;
      if (treeNode == null)
         return;

      if (treeNode.deleted == true)
      {
         removeNode(treeNode);
         mSize--;
      }

      for (child = treeNode.firstChild; child != null; child = child.sib)
         hardDelete(child);
   }

   public int size(FHsdTreeNode<E> treeNode, int level)
   {
      int vSize = 0;

      if (treeNode == null)
         return vSize;

      if (treeNode.deleted == true)
      {
         vSize += size( treeNode.sib, level );
      }else
      {
         vSize++;

         // recursive step done here
         vSize += size( treeNode.firstChild, level + 1 );
         if (level > 0)
            vSize += size( treeNode.sib, level );
      }
      return vSize;
   }

   public Object clone() throws CloneNotSupportedException
   {
      FHsdTree<E> newObject = (FHsdTree<E>)super.clone();
      newObject.clear();  // can't point to other's data

      newObject.mRoot = cloneSubtree(mRoot);
      newObject.mSize = mSize;
      newObject.setMyRoots(newObject.mRoot);

      return newObject;
   }

   private FHsdTreeNode<E> cloneSubtree(FHsdTreeNode<E> root)
   {
      FHsdTreeNode<E> newNode;
      if (root == null)
         return null;

      // does not set myRoot which must be done by caller
      newNode = new FHsdTreeNode<E>
      (
            root.data, 
            cloneSubtree(root.sib), cloneSubtree(root.firstChild),
            null, false
            );

      // the prev pointer is set by parent recursive call ... this is the code:
      if (newNode.sib != null)
         newNode.sib.prev = newNode;
      if (newNode.firstChild != null)
         newNode.firstChild.prev = newNode;
      return newNode;
   }

   // recursively sets all myRoots to mRoot
   private void setMyRoots(FHsdTreeNode<E> treeNode)
   {
      if (treeNode == null)
         return;

      treeNode.myRoot = mRoot;
      setMyRoots(treeNode.sib);
      setMyRoots(treeNode.firstChild);
   }

   // define this as a static member so recursive display() does not need
   // a local version
   final static String blankString = "                                    ";

   // let be public so client can call on subtree
   public void  displayPhysical(FHsdTreeNode<E> treeNode, int level) 
   {
      String indent;

      // stop runaway indentation/recursion
      if  (level > (int)blankString.length() - 1)
      {
         System.out.println( blankString + " ... " );
         return;
      }

      if (treeNode == null)
         return;

      indent = blankString.substring(0, level);

      // pre-order processing done here ("visit")
      System.out.println( indent + treeNode.data ) ;

      // recursive step done here
      displayPhysical( treeNode.firstChild, level + 1 );
      if (level > 0)
         displayPhysical( treeNode.sib, level );
   }

   // let be public so client can call on subtree
   public void  display(FHsdTreeNode<E> treeNode, int level) 
   {
      String indent;

      // stop runaway indentation/recursion
      if  (level > (int)blankString.length() - 1)
      {
         System.out.println( blankString + " ... " );
         return;
      }

      if (treeNode == null)
         return;

      if (treeNode.deleted == true)
      {
         display( treeNode.sib, level );
      }else
      {

         indent = blankString.substring(0, level);

         // pre-order processing done here ("visit")
         System.out.println( indent + treeNode.data + " " + treeNode.deleted ) ;

         // recursive step done here

         display( treeNode.firstChild, level + 1 );
         if (level > 0)
            display( treeNode.sib, level );
      }

   }

   // often helper of typical public version, but also callable by on subtree
   public <F extends Traverser<? super E>> 
   void traverse(F func, FHsdTreeNode<E> treeNode, int level)
   {
      if (treeNode == null)
         return;

      func.visit(treeNode.data);

      // recursive step done here
      traverse( func, treeNode.firstChild, level + 1);
      if (level > 0 )
         traverse( func,  treeNode.sib, level);
   }
}

class FHsdTreeNode<E>
{
   // use protected access so the tree, in the same package, 
   // or derived classes can access members 
   protected FHsdTreeNode<E> firstChild, sib, prev;
   protected E data;
   protected FHsdTreeNode<E> myRoot;  // needed to test for certain error
   protected boolean deleted; // true if the node has been removed from the tree

   public FHsdTreeNode( E d, FHsdTreeNode<E> sb, FHsdTreeNode<E> chld,
         FHsdTreeNode<E> prv, boolean del  )
   {
      firstChild = chld; 
      sib = sb;
      prev = prv;
      data = d;
      myRoot = null;
      deleted = del;
   }

   public FHsdTreeNode()
   {
      this(null, null, null, null, false);
   }

   public E getData() { return data; }

   // for use only by FHtree (default access)
   protected FHsdTreeNode( E d, FHsdTreeNode<E> sb, FHsdTreeNode<E> chld, 
         FHsdTreeNode<E> prv, FHsdTreeNode<E> root, boolean del)
   {
      this(d, sb, chld, prv, del);
      myRoot = root;
   }
}

interface Traverser<E>
{
   public void visit(E x);
}

/* Sample Run ------------------------------------------------------------------
--------------------------------------------------------------------------------
Virtual List After Initial Additions:
--------------------------------------------------------------------------------
room false
 table false
  south west leg false
  south east leg false
  north west leg false
  north east leg false
 Miguel the human false
  torso false
   right arm false
    right hand false
     pinky false
     ring finger false
     middle finger false
     index finger false
     thumb false
   left arm false
    left hand false
     pinky false
     ring finger false
     middle finger false
     index finger false
     thumb false
 Lily the canine false
  torso false
   wagging tail false
   spare mutant paw false
   left rear paw false
   right rear paw false
   left front paw false
   right front paw false

Virtual Size: 30
Physical Size: 30

--------------------------------------------------------------------------------
Physical List After Initial Additions:
--------------------------------------------------------------------------------
room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Miguel the human
  torso
   right arm
    right hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
   left arm
    left hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
 Lily the canine
  torso
   wagging tail
   spare mutant paw
   left rear paw
   right rear paw
   left front paw
   right front paw

Virtual Size: 30
Physical Size: 30

--------------------------------------------------------------------------------
Virtual List After Removal:
--------------------------------------------------------------------------------
room false
 table false
  south west leg false
  south east leg false
  north west leg false
  north east leg false
 Lily the canine false
  torso false
   wagging tail false
   left rear paw false
   right rear paw false
   left front paw false
   right front paw false

Virtual Size: 13
Physical Size: 30

--------------------------------------------------------------------------------
Physical List After Removal:
--------------------------------------------------------------------------------
room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Miguel the human
  torso
   right arm
    right hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
   left arm
    left hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
 Lily the canine
  torso
   wagging tail
   spare mutant paw
   left rear paw
   right rear paw
   left front paw
   right front paw

Virtual Size: 13
Physical Size: 30

--------------------------------------------------------------------------------
Virtual List After Garbage Colletction:
--------------------------------------------------------------------------------
room false
 table false
  south west leg false
  south east leg false
  north west leg false
  north east leg false
 Lily the canine false
  torso false
   wagging tail false
   left rear paw false
   right rear paw false
   left front paw false
   right front paw false

Virtual Size: 13
Physical Size: 13

--------------------------------------------------------------------------------
Physical List After Garbage Colletction:
--------------------------------------------------------------------------------
room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Lily the canine
  torso
   wagging tail
   left rear paw
   right rear paw
   left front paw
   right front paw

Virtual Size: 13
Physical Size: 13

--------------------------------------------------------------------------------
Clone display
--------------------------------------------------------------------------------
room false
 table false
  south west leg false
  south east leg false
  north west leg false
  north east leg false
 Miguel the human false
  torso false
   right arm false
    right hand false
     pinky false
     ring finger false
     middle finger false
     index finger false
     thumb false
   left arm false
    left hand false
     pinky false
     ring finger false
     middle finger false
     index finger false
     thumb false
 Lily the canine false
  torso false
   wagging tail false
   spare mutant paw false
   left rear paw false
   right rear paw false
   left front paw false
   right front paw false

Clone's Virtual Size: 30
Clone's Physical Size: 30


----------------------------------------------------------------------------- */


