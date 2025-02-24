package com.example.counteriesinfoapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.counteriesinfoapp.data.global.model.CountryResponse
import com.example.counteriesinfoapp.ui.theme.Purple40

@Composable
fun CountryItem(country: CountryResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = country.media.flag,
                contentDescription = "${country.name} flag",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = country.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End) {
                Text(
                    text = "Capital: ${country.capital}",
                    style = MaterialTheme.typography.caption,
                    color = Purple40
                )
                Text(
                    text = "Currency: ${country.currency}",
                    style = MaterialTheme.typography.caption,
                    color = Purple40
                )
                Text(
                    text = "Population: ${country.population}",
                    style = MaterialTheme.typography.caption,
                    color = Purple40
                )
            }
        }
    }
}

