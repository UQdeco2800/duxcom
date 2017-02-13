//package uq.deco2800.duxcom.maps.mapgen.textures;
//
//import org.junit.After;
//import org.junit.Ignore;
//import uq.deco2800.duxcom.dataregisters.OldSelectionRegister;
//import uq.deco2800.duxcom.dataregisters.OldTileRegister;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//
///**
// * Will eventually work if the image register is ever static
// *
// * Created by liamdm on 18/08/2016.
// */
//public class TextureTranslatorTest {
//    ArrayList<String> translateTests = new ArrayList<String>(){{
//        add("grass_1");
//        add("grass_2");
//        add("grass_3");
//    }};
//
//    @Ignore
//    public void setUp() throws Exception {
//        for (String resource : translateTests) {
//            hasResource:
//            {
//                try {
//                    if (OldTileRegister.getTileImageLocation(resource) != null ||
//                            OldEntityRegister.getEntityImageLocation(resource) != null ||
//                            OldSelectionRegister.getSelectionImageLocation(resource) != null) {
//                        break hasResource;
//                    }
//                } catch (Exception ex) {
//                    // FALLTHROUGH
//                }
//
//                // Missing - abort test
//                assertFalse("Missing resource " + resource + "! Please re-write unit test!", true);
//            }
//        }
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//
//    }
//
//    @Ignore
//    public void translationTest() throws Exception {
//        TextureTranslator textureTranslator = new TextureTranslator();
//        for(String name : translateTests) {
//            textureTranslator.addTranslation("new_" + name, name);
//        }
//
//        for(String name : translateTests){
//            assertEquals("new_" + name, textureTranslator.translate(name));
//        }
//    }
//
//}