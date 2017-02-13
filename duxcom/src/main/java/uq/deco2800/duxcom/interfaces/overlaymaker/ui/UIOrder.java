package uq.deco2800.duxcom.interfaces.overlaymaker.ui;

/**
 * Enumeration of UI Z Ordering
 * @author The_Magic_Karps
 */
public enum UIOrder {
    VERY_BACK {
        @Override
        public int getOrder() {
            return 10;
        }
    }, BACK {
        @Override
        public int getOrder() {
            return 15;
        }
    }, MIDDLE {
        @Override
        public int getOrder() {
            return 20;
        }
    }, FRONT {
        @Override
        public int getOrder() {
            return 25;
        }
    }, VERY_FRONT {
        @Override
        public int getOrder() {
            return 30;
        }
    };

    public abstract int getOrder();
}
