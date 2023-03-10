import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'master-lookup',
        data: { pageTitle: 'smartHrApp.masterLookup.home.title' },
        loadChildren: () => import('./master-lookup/master-lookup.module').then(m => m.MasterLookupModule),
      },
      {
        path: 'form-validator',
        data: { pageTitle: 'smartHrApp.formValidator.home.title' },
        loadChildren: () => import('./form-validator/form-validator.module').then(m => m.FormValidatorModule),
      },
      {
        path: 'employee',
        data: { pageTitle: 'smartHrApp.employee.home.title' },
        loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule),
      },
      {
        path: 'reporting-structure',
        data: { pageTitle: 'smartHrApp.reportingStructure.home.title' },
        loadChildren: () => import('./reporting-structure/reporting-structure.module').then(m => m.ReportingStructureModule),
      },
      {
        path: 'personal-details',
        data: { pageTitle: 'smartHrApp.personalDetails.home.title' },
        loadChildren: () => import('./personal-details/personal-details.module').then(m => m.PersonalDetailsModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'smartHrApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'family-info',
        data: { pageTitle: 'smartHrApp.familyInfo.home.title' },
        loadChildren: () => import('./family-info/family-info.module').then(m => m.FamilyInfoModule),
      },
      {
        path: 'contacts',
        data: { pageTitle: 'smartHrApp.contacts.home.title' },
        loadChildren: () => import('./contacts/contacts.module').then(m => m.ContactsModule),
      },
      {
        path: 'banks-details',
        data: { pageTitle: 'smartHrApp.banksDetails.home.title' },
        loadChildren: () => import('./banks-details/banks-details.module').then(m => m.BanksDetailsModule),
      },
      {
        path: 'education',
        data: { pageTitle: 'smartHrApp.education.home.title' },
        loadChildren: () => import('./education/education.module').then(m => m.EducationModule),
      },
      {
        path: 'work-experience',
        data: { pageTitle: 'smartHrApp.workExperience.home.title' },
        loadChildren: () => import('./work-experience/work-experience.module').then(m => m.WorkExperienceModule),
      },
      {
        path: 're-enumeration',
        data: { pageTitle: 'smartHrApp.reEnumeration.home.title' },
        loadChildren: () => import('./re-enumeration/re-enumeration.module').then(m => m.ReEnumerationModule),
      },
      {
        path: 'pf-details',
        data: { pageTitle: 'smartHrApp.pfDetails.home.title' },
        loadChildren: () => import('./pf-details/pf-details.module').then(m => m.PfDetailsModule),
      },
      {
        path: 'esi-details',
        data: { pageTitle: 'smartHrApp.esiDetails.home.title' },
        loadChildren: () => import('./esi-details/esi-details.module').then(m => m.EsiDetailsModule),
      },
      {
        path: 'company',
        data: { pageTitle: 'smartHrApp.company.home.title' },
        loadChildren: () => import('./company/company.module').then(m => m.CompanyModule),
      },
      {
        path: 'region',
        data: { pageTitle: 'smartHrApp.region.home.title' },
        loadChildren: () => import('./region/region.module').then(m => m.RegionModule),
      },
      {
        path: 'branch',
        data: { pageTitle: 'smartHrApp.branch.home.title' },
        loadChildren: () => import('./branch/branch.module').then(m => m.BranchModule),
      },
      {
        path: 'state',
        data: { pageTitle: 'smartHrApp.state.home.title' },
        loadChildren: () => import('./state/state.module').then(m => m.StateModule),
      },
      {
        path: 'salary-settings',
        data: { pageTitle: 'smartHrApp.salarySettings.home.title' },
        loadChildren: () => import('./salary-settings/salary-settings.module').then(m => m.SalarySettingsModule),
      },
      {
        path: 'tds',
        data: { pageTitle: 'smartHrApp.tds.home.title' },
        loadChildren: () => import('./tds/tds.module').then(m => m.TdsModule),
      },
      {
        path: 'department',
        data: { pageTitle: 'smartHrApp.department.home.title' },
        loadChildren: () => import('./department/department.module').then(m => m.DepartmentModule),
      },
      {
        path: 'designation',
        data: { pageTitle: 'smartHrApp.designation.home.title' },
        loadChildren: () => import('./designation/designation.module').then(m => m.DesignationModule),
      },
      {
        path: 'leave-type',
        data: { pageTitle: 'smartHrApp.leaveType.home.title' },
        loadChildren: () => import('./leave-type/leave-type.module').then(m => m.LeaveTypeModule),
      },
      {
        path: 'leave-policy',
        data: { pageTitle: 'smartHrApp.leavePolicy.home.title' },
        loadChildren: () => import('./leave-policy/leave-policy.module').then(m => m.LeavePolicyModule),
      },
      {
        path: 'leave-application',
        data: { pageTitle: 'smartHrApp.leaveApplication.home.title' },
        loadChildren: () => import('./leave-application/leave-application.module').then(m => m.LeaveApplicationModule),
      },
      {
        path: 'holiday',
        data: { pageTitle: 'smartHrApp.holiday.home.title' },
        loadChildren: () => import('./holiday/holiday.module').then(m => m.HolidayModule),
      },
      {
        path: 'workin-days-setting',
        data: { pageTitle: 'smartHrApp.workinDaysSetting.home.title' },
        loadChildren: () => import('./workin-days-setting/workin-days-setting.module').then(m => m.WorkinDaysSettingModule),
      },
      {
        path: 'approval-setting',
        data: { pageTitle: 'smartHrApp.approvalSetting.home.title' },
        loadChildren: () => import('./approval-setting/approval-setting.module').then(m => m.ApprovalSettingModule),
      },
      {
        path: 'approver',
        data: { pageTitle: 'smartHrApp.approver.home.title' },
        loadChildren: () => import('./approver/approver.module').then(m => m.ApproverModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
