package br.com.johabfreitas.apiviacep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.johabfreitas.apiviacep.databinding.ActivityMainBinding
import br.com.johabfreitas.apiviacep.model.Endereco
import br.com.johabfreitas.apiviacep.service.EnderecoAPI
import br.com.johabfreitas.apiviacep.service.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.create
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                recuperarEndereco()
                binding.edtCEP.getText().clear()
            }
        }
    }

    private suspend fun recuperarEndereco() {

        var retorno: Response<Endereco>? = null
        val cepDigitado = binding.edtCEP.text.toString()

        try {
            val enderecoAPI = retrofit.create(EnderecoAPI::class.java)
            retorno = enderecoAPI.recuperarEndereco(cepDigitado)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao buscar", Toast.LENGTH_LONG).show()
            Log.i("info_buscar", "Erro ao buscar")
        }

        if (retorno != null) {
            if (retorno.isSuccessful) {

                val endereco = retorno.body()

                binding.edtEndereco.setText(endereco?.logradouro)
                binding.edtComplemento.setText(endereco?.complemento)
                binding.edtBairro.setText(endereco?.bairro)
                binding.edtLocalidade.setText(endereco?.localidade)
                binding.edtUF.setText(endereco?.uf)
                binding.edtIbge.setText(endereco?.ibge)
                binding.edtGia.setText(endereco?.gia)
                binding.edtDDD.setText(endereco?.ddd)
                binding.edtSiafi.setText(endereco?.siafi)

                Toast.makeText(this, "Busca realizada com sucesso", Toast.LENGTH_LONG).show()

            }
        }

    }
}