package gomedic.logic.commands;

import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.person.doctor.DoctorId;
import gomedic.model.person.patient.PatientId;

class ReferralCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_nonExistentPatient_throwsCommandException() {
        ReferralCommand referralCommand = new ReferralCommand(
                new Title("test"),
                new DoctorId(1),
                new PatientId(999),
                new Description("test")
        );
        assertThrows(CommandException.class, () -> referralCommand.execute(model));
    }

    @Test
    public void execute_nonExistentDoctor_throwsCommandException() {
        ReferralCommand referralCommand = new ReferralCommand(
                new Title("test"),
                new DoctorId(999),
                new PatientId(1),
                new Description("test")
        );
        assertThrows(CommandException.class, () -> referralCommand.execute(model));
    }


    @Test
    public void execute_emptyTitle_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ReferralCommand(
                new Title(""),
                new DoctorId(999),
                new PatientId(1),
                new Description("test")
        ));
    }

    @Test
    public void equals() {
        ReferralCommand referralCommand = new ReferralCommand(
                new Title("test"),
                new DoctorId(1),
                new PatientId(1),
                new Description("")
        );

        ReferralCommand referralCommandTwo = new ReferralCommand(
                new Title("test"),
                new DoctorId(1),
                new PatientId(1),
                new Description("")
        );

        assertEquals(referralCommandTwo, referralCommand);
        assertEquals(referralCommandTwo, referralCommandTwo);

        ReferralCommand referralCommandDiffDesc = new ReferralCommand(
                new Title("test"),
                new DoctorId(1),
                new PatientId(1),
                new Description("not equal")
        );

        assertNotEquals(referralCommandTwo, referralCommandDiffDesc);
    }
}
