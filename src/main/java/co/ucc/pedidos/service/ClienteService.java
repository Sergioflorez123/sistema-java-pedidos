package co.ucc.pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.ucc.pedidos.model.ClienteModel;
import co.ucc.pedidos.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public ClienteModel registrarCliente(ClienteModel cliente) {
        if (cliente == null) {
            throw new co.ucc.pedidos.exception.ResourceNotFoundException("El cliente no puede ser nulo");
        }
        if (clienteRepository.existsByIdCliente(cliente.getIdCliente())) {
            throw new co.ucc.pedidos.exception.ResourceAlreadyExistsException("Ya existe un cliente con el ID: " + cliente.getIdCliente());
        }
        if (clienteRepository.existsByCorreoElectronico(cliente.getCorreoElectronico())) {
            throw new co.ucc.pedidos.exception.ResourceAlreadyExistsException("Ya existe un cliente con el correo: " + cliente.getCorreoElectronico());
        }
        return clienteRepository.save(cliente);
    }

    public List<ClienteModel> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<ClienteModel> buscarPorId(String idCliente) {
        return clienteRepository.findByIdCliente(idCliente);
    }

    public Optional<ClienteModel> buscarPorCorreo(String correoElectronico) {
        return clienteRepository.findByCorreoElectronico(correoElectronico);
    }

    public Optional<ClienteModel> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombre(nombre);
    }

    @Transactional
    public ClienteModel actualizarCliente(String idCliente, ClienteModel clienteActualizado) {
        ClienteModel clienteExistente = clienteRepository.findByIdCliente(idCliente)
                .orElseThrow(() -> new co.ucc.pedidos.exception.ResourceNotFoundException("Cliente no encontrado con ID: " + idCliente));

        if (clienteActualizado.getGenero() != null) {
            clienteExistente.setGenero(clienteActualizado.getGenero());
        }
        if (clienteActualizado.getNombre() != null) {
            clienteExistente.setNombre(clienteActualizado.getNombre());
        }
        if (clienteActualizado.getCorreoElectronico() != null) {
            clienteExistente.setCorreoElectronico(clienteActualizado.getCorreoElectronico());
        }
        if (clienteActualizado.getDireccion() != null) {
            clienteExistente.setDireccion(clienteActualizado.getDireccion());
        }

        return clienteRepository.save(clienteExistente);
    }

    @Transactional
    public void eliminarCliente(String idCliente) {
        if (!clienteRepository.existsByIdCliente(idCliente)) {
            throw new co.ucc.pedidos.exception.ResourceNotFoundException("Cliente no encontrado con ID: " + idCliente);
        }
        clienteRepository.deleteByIdCliente(idCliente);
    }

    public boolean existePorId(String idCliente) {
        return clienteRepository.existsByIdCliente(idCliente);
    }

    public boolean existePorCorreo(String correoElectronico) {
        return clienteRepository.existsByCorreoElectronico(correoElectronico);
    }
}
