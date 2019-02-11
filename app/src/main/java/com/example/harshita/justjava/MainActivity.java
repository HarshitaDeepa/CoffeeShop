package com.example.harshita.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreame = (CheckBox) findViewById(R.id.whippedCreameCheckbox);
        Boolean hasWhippedCream = whippedCreame.isChecked();

        CheckBox chocolateCreame = (CheckBox) findViewById(R.id.choclateCheckbox);
        Boolean hasChocolateCream = chocolateCreame.isChecked();

        int price = CalculatePrice(quantity, hasWhippedCream, hasChocolateCream);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolateCream, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java aap for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolateCream is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    public String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolateCream, String name)
    {

        String summary = getString(R.string.order_summary_name, name) + "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream) + "\n" + getString(R.string.order_summary_chocolate, addChocolateCream) + "\n" + getString(R.string.order_summary_quantity, quantity) + "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price)) + " \n" + getString(R.string.thank_you);
        return summary;
    }

    public int CalculatePrice(int quantity, boolean addWhippedCream, boolean addChocolateCream)
    {
        int price = 5;
        if(addChocolateCream == true)
        {
            price = price + 3;
        }
        if(addWhippedCream == true)
        {
            price = price + 2;
        }
        return quantity * price;
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view)
    {
        if(quantity < 100)
         quantity += 1;
        else
        {
            Toast.makeText(this, "Maximum number of coffee cups can be 100!", Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view)
    {
        if(quantity > 1)
         quantity -= 1;
        else
        {
            Toast.makeText(this, "Minimum number of coffee cups can be 1!", Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
