package com.example.ttt2;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static int now = 0;
    private static int next = 1;
    private static int[] XColCount = {0, 0, 0};
    private static int XPosDiaCount = 0;
    private static int XNegDiaCount = 0;
    private static int[] XRowCount = {0, 0, 0};
    private static int[] OColCount = {0, 0, 0};
    private static int OPosDiaCount = 0;
    private static int ONegDiaCount = 0;
    private static int[] ORowCount = {0, 0, 0};

    public static String getNow() {
        String turn;
        now++;
        if (now%2 != 0) {
            turn = "X";
        } else {
            turn = "O";
        }
        return turn;
    }

    public static String getNext() {
        String turn;
        next++;
        if (next%2 != 0) {
            turn = "X";
        } else {
            turn = "O";
        }
        return turn;
    }


    public void putThenCount(TextView nowOnBoard, TextView nextTeller) {
        String now = getNow();
        String next = getNext();
        nowOnBoard.setText(now);
        nextTeller.setText("Turn: " + next);
        int row = Integer.parseInt(getId(nowOnBoard).substring(21, 22));
        int col = Integer.parseInt(getId(nowOnBoard).substring(23, 24));
        int sumRowCol = row + col;

        switch (now) {
            case "X" :
                XRowCount[row-1]++;
                XColCount[col-1]++;
                if (sumRowCol%2 == 0) {
                    if (sumRowCol == 4) {
                        if (row == 2 && col == 2) {
                            XNegDiaCount++;
                            XPosDiaCount++;
                        } else {
                            XPosDiaCount++;
                        }
                    } else {
                        XNegDiaCount++;
                    }
                }
                break;
            case "O":
                ORowCount[row-1]++;
                OColCount[col-1]++;
                if (sumRowCol%2 == 0) {
                    if (sumRowCol == 4) {
                        if (row == 2 && col == 2) {
                            ONegDiaCount++;
                            OPosDiaCount++;
                        } else {
                            OPosDiaCount++;
                        }
                    } else {
                        ONegDiaCount++;
                    }
                }
                break;
        }
        String winner;
        if (XRowCount[row-1] == 3 ||
            XColCount[col-1] == 3 ||
            XNegDiaCount == 3 ||
            XPosDiaCount == 3) {

            winner = "X";
            View linearLayout = findViewById(R.id.linearLayout);
            Snackbar snackbar = Snackbar.make(linearLayout, winner + " wins", 5000)
                .setAction("PLAY AGAIN", v -> resetListener(findViewById(R.id.resetButton)));
            snackbar.setActionTextColor(Color.BLUE);
            snackbar.show();
        }
        if (ORowCount[row-1] == 3 ||
            OColCount[col-1] == 3 ||
            ONegDiaCount == 3 ||
            OPosDiaCount == 3) {
                winner = "O";
                View linearLayout = findViewById(R.id.linearLayout);
                Snackbar snackbar = Snackbar.make(linearLayout, winner + " wins", 5000)
                        .setAction("PLAY AGAIN", v -> resetListener(findViewById(R.id.resetButton)));
                snackbar.setActionTextColor(Color.BLUE);
                snackbar.show();
        }
    }//5342568

    public void boardClickListener(View view) {
        TextView nowOnBoard = (TextView) view;
        TextView nextTeller = (TextView) findViewById(R.id.nextTeller);
        putThenCount(nowOnBoard, nextTeller);
    }

    public void resetListener(View view) {
        now = 0;
        next = 1;
        XColCount = new int[]{0, 0, 0};
        XPosDiaCount = 0;
        XNegDiaCount = 0;
        XRowCount = new int[]{0, 0, 0};
        OColCount = new int[]{0, 0, 0};
        OPosDiaCount = 0;
        ONegDiaCount = 0;
        ORowCount = new int[]{0, 0, 0};
        TextView nextTeller = (TextView) findViewById(R.id.nextTeller);
        nextTeller.setText("Turn: " + "X");
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View rowView = tableLayout.getChildAt(i);
            if (rowView instanceof TableRow) {
                for (int j = 0; j < ((TableRow) rowView).getChildCount(); j++) {
                    View rowChild = ((TableRow) rowView).getChildAt(j);
                    TextView colView = (TextView) rowChild;
                    colView.setText("-");
                }
            }
        }
    }

    public String getId(View view) {
        if (view.getId() == View.NO_ID) {
            return "no-id";
        } else {
            return view.getResources().getResourceName(view.getId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}