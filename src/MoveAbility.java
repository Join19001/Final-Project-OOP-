public class MoveAbility {
    public void move(int panjangTubuh, int[]x, int[]y, int ukuranPetak, char arah){
        for(int i = panjangTubuh; i>0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch(arah){
            case 'U':
                y[0] = y[0] - ukuranPetak;
            break;

            case 'D':
                y[0] = y[0] + ukuranPetak;
            break;

            case 'R':
                x[0] = x[0] + ukuranPetak;
            break;

            case 'L':
                x[0] = x[0] - ukuranPetak;
            break;
        }
    }
}