package studio.dinhduc.doctruyen.ui.Rule;

/**
 * Created by duy on 07/11/2016.
 */

/* một từ chỉ có tối đa 3 nguyên âm */
public class Rule4 implements Rule {
    @Override
    public boolean checkInvalidate(String word) {
        String consonant = "qrtpsdghklxcvbnmđQRTPSDGHKLXCVBNMĐ";
        int count = 0;
        for (int i=0; i<word.length(); i++){
            if (consonant.contains("" + word.charAt(i))){
                count++;
            }
        }

        return count < (word.length()-3);
    }
}
