/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText addName = (EditText) findViewById(R.id.name_input);
        CheckBox addWhipped = (CheckBox) findViewById(R.id.whipped_cream_select);
        CheckBox addChocolate = (CheckBox) findViewById(R.id.chocolate_select);
        //display(quantity);
        String quantityMessage = "Total Item Count " + quantity + " coffees.";
        String priceMessage = "Amount Due: " + "$" +  quantity*5;
        //displayMessage(quantityMessage);
        //displayMessage(priceMessage);
        orderSummary(addName.getText().toString(), quantity ,5, addWhipped.isChecked(), addChocolate.isChecked());
    }

    private int calculatePrice(int quantity, int coffeePrice, int whippedCreamPrice, int chocolatePrice, boolean whippedCream, boolean chocolate){
        int whippedCreamCost = whippedCream ? (whippedCreamPrice*quantity) : 0;
        int chocolateCost = chocolate ? (chocolatePrice*quantity) : 0;
        int coffeeCost = coffeePrice*quantity;
        int totalPrice = coffeeCost + whippedCreamCost + chocolateCost;

        return totalPrice;
    }

    private String createOrderSummary(String name, int quantity, int totalPrice,  boolean whippedCream, boolean chocolate){
        String orderMessage = "Name:"+name+"\n";
        orderMessage += "Quantity:" + quantity + "\n";
        orderMessage += "Add whipped cream:"+whippedCream + "\n";
        orderMessage += "Add chocolate:"+chocolate+"\n";
        orderMessage += "Total:$"+totalPrice + "\n";
        orderMessage += getString(R.string.thank_you);

        return orderMessage;
    }
    public void orderSummary(String name, int quantity, int price, boolean whippedCream, boolean chocolate){
        int totalPrice = calculatePrice(quantity, price, 1, 2, whippedCream, chocolate);

        String orderMessage = createOrderSummary(name, quantity, totalPrice, whippedCream, chocolate);

        String subject = "JustJava Order for " + name;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(orderMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }


    public void increment(View view){
        quantity++;
        quantity = (quantity > 100) ? 100 : quantity;
        displayQuantity(quantity);
    }

    public void decrement(View view){
        quantity--;
        quantity = (quantity < 1) ? 1 : quantity;

        displayQuantity(quantity);
    }

}