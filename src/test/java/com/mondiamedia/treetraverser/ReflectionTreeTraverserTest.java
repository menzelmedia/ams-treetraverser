package com.mondiamedia.treetraverser;

/*
 */
import com.mondiamedia.treetraverser.TreeIteratorHandler;
import com.mondiamedia.treetraverser.ReflectionTreeTraverser;
import java.util.LinkedList;
import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

/**
 *
 * @author MENZE07
 */
public class ReflectionTreeTraverserTest extends TestCase {

    Logger logger = Logger.getLogger(this.getClass());

    /**
     * Simple Example Handler
     */
    public class Handler implements TreeIteratorHandler {
        // a simple Test handler

        public List<String> seenCats = new LinkedList<String>();
        public List<String> seenKittens = new LinkedList<String>();
        public List<Integer> seenAges = new LinkedList<Integer>();

        public void enter(Kitten kitten) {
            logger.info("handling kitten: " + kitten.getName());
            seenKittens.add(kitten.getName());
        }


        public void leave(Cat cat) {
            logger.info("handling cat: " + cat.getName() + " has " + cat.getKittens().size() + " kittens");
            seenCats.add(cat.getName());
        }

        public void enter(KittenAge age) {
            seenAges.add(age.getAge());
        }
    }

    public class KittenAge {

        private int age;

        public KittenAge(int age) {
            this.age = age;
        }

        /**
         * @return the age
         */
        public int getAge() {
            return age;
        }

        /**
         * @param age the age to set
         */
        public void setAge(int age) {
            this.age = age;
        }
    }

    public class Kitten {

        private String name;
        private KittenAge age;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the age
         */
        public KittenAge getAge() {
            return age;
        }

        /**
         * @param age the age to set
         */
        public void setAge(KittenAge age) {
            this.age = age;
        }
    }

    public class Cat {

        private String name;
        private List<Kitten> kittens = new LinkedList<Kitten>();

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the kittens
         */
        public List<Kitten> getKittens() {
            return kittens;
        }

        /**
         * @param kittens the kittens to set
         */
        public void setKittens(List<Kitten> kittens) {
            this.kittens = kittens;
        }
    }

    public ReflectionTreeTraverserTest(String testName) {
        super(testName);
        /*
         org.apache.log4j.Logger.getRootLogger().setLevel(Level.DEBUG);
         org.apache.log4j.Logger.getRootLogger().addAppender(new ConsoleAppender());
         * */
    }

    public void testTreeTraverse() throws Exception {
        Handler handler = new Handler();

        Cat cat = new Cat();
        cat.setName("Hesekiel");
        Kitten kitten = new Kitten();
        kitten.setName("Panterle");
        kitten.setAge(new KittenAge(3));
        cat.getKittens().add(kitten);

        kitten = new Kitten();
        kitten.setName("RolfPeter");
        kitten.setAge(new KittenAge(1));
        cat.getKittens().add(kitten);

        ReflectionTreeTraverser traverser = new ReflectionTreeTraverser();
        traverser.setHandler(handler);
        traverser.treeTraverse(cat);

        assertEquals(1, handler.seenCats.size());
        assertEquals(2, handler.seenKittens.size());



        assertEquals("Hesekiel", handler.seenCats.get(0));
        assertEquals("Panterle", handler.seenKittens.get(0));
        assertEquals("RolfPeter", handler.seenKittens.get(1));

        assertEquals(2, handler.seenAges.size());
        assertEquals(3, (int) handler.seenAges.get(0));
        assertEquals(1, (int) handler.seenAges.get(1));


    }
}
