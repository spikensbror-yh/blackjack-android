package blackjack.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import blackjack.game.Card;

public class CardListAdapter extends ArrayAdapter<Card> {
    public CardListAdapter(Context context, List<Card> objects) {
        super(context, 0, objects);
    }

    //region Overrides

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_view, parent, false);
        }

        TextView cardDescription = (TextView)convertView.findViewById(R.id.cardDescription);
        cardDescription.setText(card.toString());

        return convertView;
    }

    //endregion
}
