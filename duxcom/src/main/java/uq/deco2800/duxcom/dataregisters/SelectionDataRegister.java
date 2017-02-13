package uq.deco2800.duxcom.dataregisters;

/**
 * Links each selection marker type from the SelectionType enum with a set of data from
 * SelectionDataClass.
 *
 * @author liamdm
 * @author Alex McLean
 */
public class SelectionDataRegister extends AbstractDataRegister<SelectionType, SelectionDataClass> {

    /**
     * Package private constructor
     *
     * Should only be called by DataRegisterManager
     */
    SelectionDataRegister() {
        super();

        linkDataToType(SelectionType.SELECTION_BASIC, new SelectionDataClass("selection_basic", "selection"));
        linkDataToType(SelectionType.SELECTION_STANDARD, new SelectionDataClass("real_selection_standard", "real_selection_standard"));
        linkDataToType(SelectionType.SELECTION_FAIL, new SelectionDataClass("real_selection_fail", "real_selection_fail"));
        linkDataToType(SelectionType.SELECTION_SPECIAL, new SelectionDataClass("real_selection_special", "real_selection_special"));
        linkDataToType(SelectionType.SELECTION_TURNMARKER, new SelectionDataClass("real_turnmarker", "real_turnmarker"));
    }
}
